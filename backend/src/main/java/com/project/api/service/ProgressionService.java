package com.project.api.service;

import com.project.api.entity.*;
import com.project.api.repository.*;
import com.project.api.dto.ProgressionJournaliereRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ProgressionService {
    
    private final ProgressionJournaliereRepository progressionRepository;
    private final UserProgrammeRepository userProgrammeRepository;
    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final PlatRepository platRepository;
    private final ActiviteSportiveRepository activiteSportiveRepository;
    
    @Autowired
    public ProgressionService(ProgressionJournaliereRepository progressionRepository,
                             UserProgrammeRepository userProgrammeRepository,
                             UserRepository userRepository,
                             BadgeRepository badgeRepository,
                             PlatRepository platRepository,
                             ActiviteSportiveRepository activiteSportiveRepository) {
        this.progressionRepository = progressionRepository;
        this.userProgrammeRepository = userProgrammeRepository;
        this.userRepository = userRepository;
        this.badgeRepository = badgeRepository;
        this.platRepository = platRepository;
        this.activiteSportiveRepository = activiteSportiveRepository;
    }
    
    @Transactional
    public ProgressionJournaliere enregistrerProgression(Long userId, ProgressionJournaliereRequest request) {
        UserProgramme userProgramme = getActiveUserProgramme(userId);
        
        // 🔴 PROBLEM 10 - Vérifier que le programme n'est pas en pause
        if (!"EN_COURS".equals(userProgramme.getStatut())) {
            throw new RuntimeException("Programme non actif");
        }
        
        LocalDate date = request.getDate() != null ? request.getDate() : LocalDate.now();
        
        // ✅ SUPPRESSION TOTALE DE LA VALIDATION DES DATES
        // Le backend accepte maintenant toutes les dates sans restriction
        
        // 🟢 FIXED - Create progression if it doesn't exist, or use existing one
        ProgressionJournaliere progression = progressionRepository
            .findByUserProgrammeIdAndDate(userProgramme.getId(), date)
            .orElseGet(() -> {
                // Create new progression if none exists for this date
                ProgressionJournaliere newProgression = new ProgressionJournaliere();
                newProgression.setUserProgramme(userProgramme);
                newProgression.setDate(date);
                newProgression.setStatutJour("NON_FAIT");
                newProgression.setScoreJour(0);
                
                // 🔧 FIX: Calculate and set jour_programme (flexible calculation)
                LocalDate programStart = userProgramme.getDateDebut();
                long daysSinceStart = ChronoUnit.DAYS.between(programStart, date) + 1;
                // Allow any day number, even negative or beyond programme duration
                newProgression.setJourProgramme((int) daysSinceStart);
                
                return newProgression;
            });
        
        // Mettre à jour les plats et activités
        if (request.getPlatIds() != null && !request.getPlatIds().isEmpty()) {
            List<Plat> plats = platRepository.findAllById(request.getPlatIds());
            progression.setPlatsConsommes(plats);
        }
        
        if (request.getActiviteIds() != null && !request.getActiviteIds().isEmpty()) {
            List<ActiviteSportive> activites = activiteSportiveRepository.findAllById(request.getActiviteIds());
            progression.setActivitesRealisees(activites);
        }
        
        // Mettre à jour le poids
        if (request.getPoidsJour() != null) {
            progression.setPoidsJour(request.getPoidsJour());
            userProgramme.setPoidsActuel(request.getPoidsJour());
            userProgrammeRepository.save(userProgramme);
        }
        
        // Mettre à jour les notes
        if (request.getNotes() != null) {
            progression.setNotes(request.getNotes());
        }
        
        // Calculer le statut et le score
        calculerStatutEtScore(progression, userProgramme);
        
        // Calculer les calories
        progression.calculerCalories();
        
        ProgressionJournaliere saved = progressionRepository.save(progression);
        
        // Vérifier et attribuer des badges
        verifierBadges(userId, userProgramme);
        
        return saved;
    }
    
    private void calculerStatutEtScore(ProgressionJournaliere progression, UserProgramme userProgramme) {
        int platsConsommes = progression.getPlatsConsommes().size();
        int activitesRealisees = progression.getActivitesRealisees().size();
        
        // 🟠 PROBLEM 11 - Score plus nuancé basé sur le nombre d'éléments
        int scoreRepas = Math.min(platsConsommes * 15, 50);  // Max 50 points pour les repas
        int scoreActivites = Math.min(activitesRealisees * 15, 30);  // Max 30 points pour les activités
        int bonusCompletude = 0;
        
        // Bonus si tous les éléments du programme sont respectés
        int platsAttendus = userProgramme.getProgramme().getPlats().size();
        int activitesAttendues = userProgramme.getProgramme().getActivites().size();
        
        if (platsConsommes >= platsAttendus && activitesRealisees >= activitesAttendues) {
            bonusCompletude = 20;  // Bonus pour complétion totale
        }
        
        int scoreTotal = scoreRepas + scoreActivites + bonusCompletude;
        progression.setScoreJour(Math.min(scoreTotal, 100));
        
        if (scoreTotal >= 80) {
            progression.setStatutJour("COMPLETE");
        } else if (scoreTotal >= 30) {
            progression.setStatutJour("PARTIEL");
        } else {
            progression.setStatutJour("NON_FAIT");
        }
    }
    
    private void verifierBadges(Long userId, UserProgramme userProgramme) {
        List<ProgressionJournaliere> progressions = progressionRepository
            .findByUserProgrammeId(userProgramme.getId());
        
        // Calculer le streak
        int streak = calculerStreak(progressions);
        
        // Badge Débutant: 7 jours consécutifs
        if (streak >= 7) {
            attribuerBadge(userId, "DEBUTANT", "Débutant", "7 jours consécutifs complétés!", "🔥");
        }
        
        // Badge Régulier: 14 jours consécutifs
        if (streak >= 14) {
            attribuerBadge(userId, "REGULIER", "Régulier", "14 jours consécutifs complétés!", "⭐");
        }
        
        // Badge Champion: 30 jours consécutifs
        if (streak >= 30) {
            attribuerBadge(userId, "CHAMPION", "Champion", "30 jours consécutifs complétés!", "🏆");
        }
        
        // Badge Sportif: 20 activités
        long totalActivites = progressions.stream()
            .mapToLong(p -> p.getActivitesRealisees().size())
            .sum();
        if (totalActivites >= 20) {
            attribuerBadge(userId, "SPORTIF", "Sportif", "20 activités sportives réalisées!", "💪");
        }
        
        // Badge Nutritionniste: 50 plats
        long totalPlats = progressions.stream()
            .mapToLong(p -> p.getPlatsConsommes().size())
            .sum();
        if (totalPlats >= 50) {
            attribuerBadge(userId, "NUTRITIONNISTE", "Nutritionniste", "50 plats sains consommés!", "🥗");
        }
    }
    
    private int calculerStreak(List<ProgressionJournaliere> progressions) {
        if (progressions.isEmpty()) return 0;
        
        progressions.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        
        int streak = 0;
        LocalDate dateAttendue = LocalDate.now();
        
        for (ProgressionJournaliere prog : progressions) {
            if (prog.getDate().equals(dateAttendue) && 
                ("COMPLETE".equals(prog.getStatutJour()) || "PARTIEL".equals(prog.getStatutJour()))) {
                streak++;
                dateAttendue = dateAttendue.minusDays(1);
            } else {
                break;
            }
        }
        
        return streak;
    }
    
    private void attribuerBadge(Long userId, String nomBadge, String titre, String description, String icone) {
        if (!badgeRepository.existsByUserIdAndNom(userId, nomBadge)) {
            User user = userRepository.findById(userId).orElseThrow();
            Badge badge = new Badge(user, nomBadge, titre, description, icone);
            badgeRepository.save(badge);
        }
    }
    
    public ProgressionJournaliere getProgressionByDate(Long userId, LocalDate date) {
        UserProgramme activeProgram = getActiveUserProgramme(userId);
        return progressionRepository.findByUserProgrammeIdAndDate(activeProgram.getId(), date)
            .orElse(null);
    }
    
    public ProgressionJournaliere getProgressionDuJour(Long userId) {
        UserProgramme activeProgram = getActiveUserProgramme(userId);
        return progressionRepository.findByUserProgrammeIdAndDate(activeProgram.getId(), LocalDate.now())
            .orElse(null);
    }
    
    public List<ProgressionJournaliere> getHistoriqueProgression(Long userId) {
        UserProgramme activeProgram = getActiveUserProgramme(userId);
        return progressionRepository.findByUserProgrammeIdOrderByDateDesc(activeProgram.getId());
    }
    
    /**
     * Get the user's active program with intelligent fallback logic
     */
    private UserProgramme getActiveUserProgramme(Long userId) {
        List<UserProgramme> userPrograms = userProgrammeRepository.findByUserId(userId);
        
        if (userPrograms.isEmpty()) {
            throw new RuntimeException("Aucun programme trouvé pour cet utilisateur");
        }
        
        // Priority 1: Find program with status "EN_COURS"
        UserProgramme activeProgram = userPrograms.stream()
            .filter(up -> "EN_COURS".equals(up.getStatut()))
            .findFirst()
            .orElse(null);
        
        if (activeProgram != null) {
            return activeProgram;
        }
        
        // Priority 2: Find the most recent program (by date_debut)
        activeProgram = userPrograms.stream()
            .filter(up -> !"ABANDONNE".equals(up.getStatut())) // Exclude abandoned programs
            .max((up1, up2) -> up1.getDateDebut().compareTo(up2.getDateDebut()))
            .orElse(null);
        
        if (activeProgram != null) {
            return activeProgram;
        }
        
        // Priority 3: Fallback to any program (even abandoned ones)
        return userPrograms.get(0);
    }
    
    public ProgressionJournaliere getProgressionByDateAndProgram(Long userId, LocalDate date, Long userProgrammeId) {
        // Verify that the user program belongs to the user
        UserProgramme userProgramme = userProgrammeRepository.findById(userProgrammeId)
            .orElseThrow(() -> new RuntimeException("Programme utilisateur introuvable"));
        
        if (!userProgramme.getUser().getId().equals(userId)) {
            throw new RuntimeException("Accès non autorisé à ce programme");
        }
        
        return progressionRepository.findByUserProgrammeIdAndDate(userProgrammeId, date)
            .orElse(null);
    }
    
    @Transactional
    public ProgressionJournaliere enregistrerProgressionForProgram(Long userId, ProgressionJournaliereRequest request, Long userProgrammeId) {
        // Verify that the user program belongs to the user
        UserProgramme userProgramme = userProgrammeRepository.findById(userProgrammeId)
            .orElseThrow(() -> new RuntimeException("Programme utilisateur introuvable"));
        
        if (!userProgramme.getUser().getId().equals(userId)) {
            throw new RuntimeException("Accès non autorisé à ce programme");
        }
        
        LocalDate date = request.getDate() != null ? request.getDate() : LocalDate.now();
        
        // ✅ SUPPRESSION TOTALE DE LA VALIDATION DES DATES
        // Le backend accepte maintenant toutes les dates sans restriction
        
        // 🟢 FIXED - Create progression if it doesn't exist, or use existing one
        ProgressionJournaliere progression = progressionRepository
            .findByUserProgrammeIdAndDate(userProgrammeId, date)
            .orElseGet(() -> {
                // Create new progression if none exists for this date
                ProgressionJournaliere newProgression = new ProgressionJournaliere();
                newProgression.setUserProgramme(userProgramme);
                newProgression.setDate(date);
                newProgression.setStatutJour("NON_FAIT");
                newProgression.setScoreJour(0);
                
                // 🔧 FIX: Calculate and set jour_programme (flexible calculation)
                LocalDate programStart = userProgramme.getDateDebut();
                long daysSinceStart = ChronoUnit.DAYS.between(programStart, date) + 1;
                // Allow any day number, even negative or beyond programme duration
                newProgression.setJourProgramme((int) daysSinceStart);
                
                return newProgression;
            });
        
        // Mettre à jour les plats et activités
        if (request.getPlatIds() != null && !request.getPlatIds().isEmpty()) {
            List<Plat> plats = platRepository.findAllById(request.getPlatIds());
            progression.setPlatsConsommes(plats);
        }
        
        if (request.getActiviteIds() != null && !request.getActiviteIds().isEmpty()) {
            List<ActiviteSportive> activites = activiteSportiveRepository.findAllById(request.getActiviteIds());
            progression.setActivitesRealisees(activites);
        }
        
        // Mettre à jour le poids
        if (request.getPoidsJour() != null) {
            progression.setPoidsJour(request.getPoidsJour());
            userProgramme.setPoidsActuel(request.getPoidsJour());
            userProgrammeRepository.save(userProgramme);
        }
        
        // Mettre à jour les notes
        if (request.getNotes() != null) {
            progression.setNotes(request.getNotes());
        }
        
        // Calculer le statut et le score
        calculerStatutEtScore(progression, userProgramme);
        
        // Calculer les calories
        progression.calculerCalories();
        
        ProgressionJournaliere saved = progressionRepository.save(progression);
        
        // Vérifier et attribuer des badges
        verifierBadges(userId, userProgramme);
        
        return saved;
    }
}
