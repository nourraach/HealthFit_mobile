package com.project.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.api.dto.BadgeResponse;
import com.project.api.dto.StatistiquesResponse;
import com.project.api.dto.UserProgrammeRequest;
import com.project.api.entity.Badge;
import com.project.api.entity.Programme;
import com.project.api.entity.ProgressionJournaliere;
import com.project.api.entity.User;
import com.project.api.entity.UserProgramme;
import com.project.api.exception.ResourceNotFoundException;
import com.project.api.repository.BadgeRepository;
import com.project.api.repository.ProgrammeRepository;
import com.project.api.repository.ProgressionJournaliereRepository;
import com.project.api.repository.UserProgrammeRepository;
import com.project.api.repository.UserRepository;

@Service
public class ProgrammeService {
    
    private final UserProgrammeRepository userProgrammeRepository;
    private final ProgrammeRepository programmeRepository;
    private final UserRepository userRepository;
    private final ProgressionJournaliereRepository progressionRepository;
    private final BadgeRepository badgeRepository;
    
    @Autowired
    public ProgrammeService(UserProgrammeRepository userProgrammeRepository,
                           ProgrammeRepository programmeRepository,
                           UserRepository userRepository,
                           ProgressionJournaliereRepository progressionRepository,
                           BadgeRepository badgeRepository) {
        this.userProgrammeRepository = userProgrammeRepository;
        this.programmeRepository = programmeRepository;
        this.userRepository = userRepository;
        this.progressionRepository = progressionRepository;
        this.badgeRepository = badgeRepository;
    }
    
    @Transactional(readOnly = true)
    public Programme getProgrammeById(Long id) {
        // ✅ FIXED: Load programme with collections separately to avoid MultipleBagFetchException
        try {
            // First, load the programme with plats
            Optional<Programme> programmeWithPlats = programmeRepository.findByIdWithPlats(id);
            if (programmeWithPlats.isPresent()) {
                Programme programme = programmeWithPlats.get();
                
                // Then, load the same programme with activites
                Optional<Programme> programmeWithActivites = programmeRepository.findByIdWithActivites(id);
                if (programmeWithActivites.isPresent()) {
                    // Merge the activites into the programme that already has plats
                    programme.setActivites(programmeWithActivites.get().getActivites());
                }
                
                return programme;
            }
        } catch (Exception e) {
            System.err.println("Error loading programme with collections: " + e.getMessage());
        }
        
        // ✅ Fallback to basic programme if fetch fails
        return programmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programme non trouvé avec l'id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Programme> getAllProgrammes() {
        return programmeRepository.findAll();
    }
    
    @Transactional
    public UserProgramme assignerProgramme(Long userId, UserProgrammeRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        Programme programme = programmeRepository.findById(request.getProgrammeId())
            .orElseThrow(() -> new RuntimeException("Programme non trouvé"));
        
        // Vérifier si un UserProgramme existe déjà pour cet utilisateur
        List<UserProgramme> existingProgrammes = userProgrammeRepository.findByUserId(userId);
        
        UserProgramme userProgramme;
        
        if (!existingProgrammes.isEmpty()) {
            // Mettre à jour le programme existant
            userProgramme = existingProgrammes.get(0);
            userProgramme.setProgramme(programme);
            userProgramme.setPoidsDebut(request.getPoidsDebut() != null ? request.getPoidsDebut() : user.getPoids());
            userProgramme.setPoidsObjectif(request.getPoidsObjectif());
            userProgramme.setStatut("EN_COURS");
            userProgramme.setDateFin(null); // Réactiver le programme
        } else {
            // Créer un nouveau programme
            LocalDate dateDebut = request.getDateDebut() != null ? request.getDateDebut() : LocalDate.now();
            Double poidsDebut = request.getPoidsDebut() != null ? request.getPoidsDebut() : user.getPoids();
            
            userProgramme = new UserProgramme(user, programme, dateDebut, poidsDebut, request.getPoidsObjectif());
        }
        
        return userProgrammeRepository.save(userProgramme);
    }
    
    @Transactional(readOnly = true)
    public UserProgramme getProgrammeActif(Long userId) {
        List<UserProgramme> userPrograms = userProgrammeRepository.findByUserId(userId);
        
        if (userPrograms.isEmpty()) {
            throw new RuntimeException("Aucun programme trouvé pour cet utilisateur");
        }
        
        // Priority 1: Find program with status "EN_COURS"
        UserProgramme activeProgram = userPrograms.stream()
            .filter(up -> "EN_COURS".equals(up.getStatut()))
            .findFirst()
            .orElse(null);
        
        if (activeProgram == null) {
            // Priority 2: Find the most recent program (by date_debut)
            activeProgram = userPrograms.stream()
                .filter(up -> !"ABANDONNE".equals(up.getStatut())) // Exclude abandoned programs
                .max((up1, up2) -> up1.getDateDebut().compareTo(up2.getDateDebut()))
                .orElse(null);
        }
        
        if (activeProgram == null) {
            // Priority 3: Fallback to any program (even abandoned ones)
            activeProgram = userPrograms.get(0);
        }
        
        // ✅ Load complete programme data
        if (activeProgram.getProgramme() != null) {
            Long programmeId = activeProgram.getProgramme().getId();
            try {
                Programme completeProgramme = getProgrammeById(programmeId);
                activeProgram.setProgramme(completeProgramme);
            } catch (Exception e) {
                System.err.println("Warning: Could not load complete programme data for active programme");
                // Keep the basic programme data
            }
        }
        
        return activeProgram;
    }
    
    @Transactional(readOnly = true)
    public List<UserProgramme> getHistoriqueProgrammes(Long userId) {
        System.out.println("=== SERVICE DEBUG ===");
        System.out.println("getHistoriqueProgrammes called for userId: " + userId);
        
        // ✅ BULLETPROOF SOLUTION: Avoid problematic JPA fetch entirely
        List<UserProgramme> programmes = userProgrammeRepository.findByUserId(userId);
        System.out.println("Repository returned " + programmes.size() + " programmes");
        
        // ✅ Manually load complete programme data for each
        programmes.forEach(userProgramme -> {
            if (userProgramme.getProgramme() != null) {
                Long programmeId = userProgramme.getProgramme().getId();
                try {
                    // Load complete programme with plats and activities
                    Programme completeProgramme = getProgrammeById(programmeId);
                    userProgramme.setProgramme(completeProgramme);
                } catch (Exception e) {
                    System.err.println("Warning: Could not load complete programme data for ID " + programmeId);
                    // Keep the basic programme data, just without plats/activities
                }
            }
        });
        
        System.out.println("Returning " + programmes.size() + " programmes after processing");
        System.out.println("=====================");
        return programmes;
    }
    
    @Transactional(readOnly = true)
    public List<UserProgramme> getBasicHistoriqueProgrammes(Long userId) {
        System.out.println("=== BASIC SERVICE DEBUG ===");
        System.out.println("getBasicHistoriqueProgrammes called for userId: " + userId);
        
        // ✅ ULTIMATE FALLBACK: Just return basic programmes without any complex loading
        List<UserProgramme> programmes = userProgrammeRepository.findByUserId(userId);
        System.out.println("Basic repository returned " + programmes.size() + " programmes");
        System.out.println("============================");
        
        return programmes;
    }
    
    public UserProgramme getUserProgrammeById(Long id) {
        return userProgrammeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Programme utilisateur introuvable"));
    }
    
    @Transactional(readOnly = true)
    public StatistiquesResponse getStatistiques(Long userId) {
        UserProgramme userProgramme = getProgrammeActif(userId);
        List<ProgressionJournaliere> progressions = progressionRepository
            .findByUserProgrammeId(userProgramme.getId());
        
        StatistiquesResponse stats = new StatistiquesResponse();
        
        // 🔧 CORRECTION: Calcul intelligent du jour actuel et des taux
        int joursTotal = userProgramme.getProgramme().getDureeJours();
        LocalDate dateDebut = userProgramme.getDateDebut();
        LocalDate dateFin = userProgramme.getDateFinPrevue();
        LocalDate aujourdhui = LocalDate.now();
        
        // Calcul intelligent du jour actuel basé sur les progressions réelles
        int jourActuel;
        if (progressions.isEmpty()) {
            // Pas de progressions enregistrées
            jourActuel = 1;
        } else {
            // Utiliser le nombre de jours avec des progressions + 1
            jourActuel = Math.max(1, progressions.size());
        }
        
        // S'assurer que jourActuel ne dépasse pas la durée du programme
        jourActuel = Math.min(jourActuel, joursTotal);
        
        stats.setJourActuel(jourActuel);
        stats.setJoursTotal(joursTotal);
        stats.setJoursRestants(Math.max(0, joursTotal - jourActuel));
        
        // Compter les jours complétés
        long joursCompletes = progressions.stream()
            .filter(p -> "COMPLETE".equals(p.getStatutJour()))
            .count();
        
        // 🔧 CORRECTION: Taux de complétion basé sur les progressions réelles
        int tauxCompletion = jourActuel > 0 ? (int) ((joursCompletes * 100.0) / jourActuel) : 0;
        stats.setTauxCompletion(Math.min(tauxCompletion, 100));
        
        // Calcul des plats et activités
        int totalPlats = 0;
        int totalActivites = 0;
        int totalCalories = 0;
        
        for (ProgressionJournaliere prog : progressions) {
            totalPlats += prog.getPlatsConsommes().size();
            totalActivites += prog.getActivitesRealisees().size();
            if (prog.getCaloriesConsommees() != null) {
                totalCalories += prog.getCaloriesConsommees();
            }
        }
        
        stats.setTotalPlatsConsommes(totalPlats);
        stats.setTotalActivitesRealisees(totalActivites);
        
        // 🔧 CORRECTION: Taux de repas basé sur les jours avec progressions
        long joursAvecRepas = progressions.stream()
            .filter(p -> !p.getPlatsConsommes().isEmpty())
            .count();
        int tauxRepas = jourActuel > 0 ? (int) ((joursAvecRepas * 100) / jourActuel) : 0;
        stats.setTauxRepas(Math.min(tauxRepas, 100));
        
        // 🔧 CORRECTION: Taux d'activités basé sur les jours avec progressions
        long joursAvecActivites = progressions.stream()
            .filter(p -> !p.getActivitesRealisees().isEmpty())
            .count();
        int tauxActivites = jourActuel > 0 ? (int) ((joursAvecActivites * 100) / jourActuel) : 0;
        stats.setTauxActivites(Math.min(tauxActivites, 100));
        
        // Évolution physique
        int evolutionPhysique = calculerEvolutionPhysique(userProgramme);
        stats.setEvolutionPhysique(evolutionPhysique);
        
        // 🔧 NOUVELLE LOGIQUE: Progression simple basée sur plats + activités terminés
        // Calculer le total d'éléments attendus (plats + activités) pour tous les jours
        int totalPlatsAttendu = userProgramme.getProgramme().getPlats().size() * jourActuel;
        int totalActivitesAttendu = userProgramme.getProgramme().getActivites().size() * jourActuel;
        int totalElementsAttendu = totalPlatsAttendu + totalActivitesAttendu;
        
        // Calculer le total d'éléments terminés (plats consommés + activités réalisées)
        int totalElementsTermines = totalPlats + totalActivites;
        
        // Progression simple: éléments terminés / éléments attendus * 100
        int progressionGlobale = totalElementsAttendu > 0 ? 
            (totalElementsTermines * 100) / totalElementsAttendu : 0;
        stats.setProgressionGlobale(Math.min(progressionGlobale, 100));
        
        // Debug logging pour diagnostiquer
        System.out.println("=== STATISTIQUES DEBUG (NOUVELLE LOGIQUE) ===");
        System.out.println("Progressions trouvées: " + progressions.size());
        System.out.println("Jour actuel calculé: " + jourActuel);
        System.out.println("Plats dans le programme: " + userProgramme.getProgramme().getPlats().size());
        System.out.println("Activités dans le programme: " + userProgramme.getProgramme().getActivites().size());
        System.out.println("Total plats attendu: " + totalPlatsAttendu);
        System.out.println("Total activités attendu: " + totalActivitesAttendu);
        System.out.println("Total éléments attendu: " + totalElementsAttendu);
        System.out.println("Total plats terminés: " + totalPlats);
        System.out.println("Total activités terminées: " + totalActivites);
        System.out.println("Total éléments terminés: " + totalElementsTermines);
        System.out.println("PROGRESSION GLOBALE SIMPLE: " + progressionGlobale + "%");
        System.out.println("========================");
        
        // Streak
        stats.setStreakActuel(calculerStreak(progressions));
        stats.setMeilleurStreak(calculerMeilleurStreak(progressions));
        stats.setJoursActifs((int) joursCompletes);
        
        // Poids
        stats.setPoidsDebut(userProgramme.getPoidsDebut());
        stats.setPoidsActuel(userProgramme.getPoidsActuel());
        stats.setPoidsObjectif(userProgramme.getPoidsObjectif());
        if (userProgramme.getPoidsDebut() != null && userProgramme.getPoidsActuel() != null) {
            stats.setEvolutionPoids(userProgramme.getPoidsDebut() - userProgramme.getPoidsActuel());
        }
        
        // Calories moyennes (basées sur les progressions valides seulement)
        stats.setCaloriesMoyennes(progressions.isEmpty() ? 0 : totalCalories / progressions.size());
        
        // Badges
        List<Badge> badges = badgeRepository.findByUserId(userId);
        stats.setBadges(badges.stream()
            .map(b -> new BadgeResponse(b.getId(), b.getNom(), b.getTitre(), 
                b.getDescription(), b.getIcone(), b.getDateObtention()))
            .collect(Collectors.toList()));
        
        return stats;
    }
    
    private int calculerEvolutionPhysique(UserProgramme userProgramme) {
        if (userProgramme.getPoidsDebut() == null || 
            userProgramme.getPoidsActuel() == null || 
            userProgramme.getPoidsObjectif() == null) {
            return 0;
        }
        
        double poidsDebut = userProgramme.getPoidsDebut();
        double poidsActuel = userProgramme.getPoidsActuel();
        double poidsObjectif = userProgramme.getPoidsObjectif();
        
        double objectifTotal = Math.abs(poidsObjectif - poidsDebut);
        double progressionRealisee = Math.abs(poidsActuel - poidsDebut);
        
        if (objectifTotal == 0) return 100;
        
        int pourcentage = (int) ((progressionRealisee / objectifTotal) * 100);
        return Math.min(pourcentage, 100);
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
    
    private int calculerMeilleurStreak(List<ProgressionJournaliere> progressions) {
        if (progressions.isEmpty()) return 0;
        
        progressions.sort((a, b) -> a.getDate().compareTo(b.getDate()));
        
        int meilleurStreak = 0;
        int streakActuel = 0;
        LocalDate datePrecedente = null;
        
        for (ProgressionJournaliere prog : progressions) {
            if ("COMPLETE".equals(prog.getStatutJour()) || "PARTIEL".equals(prog.getStatutJour())) {
                if (datePrecedente == null || prog.getDate().equals(datePrecedente.plusDays(1))) {
                    streakActuel++;
                    meilleurStreak = Math.max(meilleurStreak, streakActuel);
                } else {
                    streakActuel = 1;
                }
                datePrecedente = prog.getDate();
            } else {
                streakActuel = 0;
                datePrecedente = null;
            }
        }
        
        return meilleurStreak;
    }
    
    @Transactional
    public void terminerProgramme(Long userId) {
        UserProgramme userProgramme = getProgrammeActif(userId);
        userProgramme.setStatut("TERMINE");
        userProgramme.setDateFin(LocalDate.now());
        userProgrammeRepository.save(userProgramme);
        
        // Attribuer badge objectif atteint
        verifierEtAttribuerBadge(userId, "OBJECTIF_ATTEINT", "Objectif Atteint", 
            "Vous avez terminé votre programme!", "🎯");
    }
    
    @Transactional
    public void pauserProgramme(Long userId) {
        UserProgramme userProgramme = getProgrammeActif(userId);
        userProgramme.setStatut("PAUSE");
        userProgrammeRepository.save(userProgramme);
    }
    
    @Transactional
    public void reprendreProgramme(Long userId) {
        List<UserProgramme> programmes = (List<UserProgramme>) userProgrammeRepository.findByUserIdAndStatut(userId, "PAUSE");
        if (programmes.isEmpty()) {
            throw new RuntimeException("Aucun programme en pause trouvé");
        }
        UserProgramme userProgramme = programmes.get(0);
        userProgramme.setStatut("EN_COURS");
        userProgrammeRepository.save(userProgramme);
    }
    
    @Transactional
    public void supprimerUserProgramme(Long userId, Long userProgrammeId) {
        // Vérifier que l'utilisateur est propriétaire du programme
        UserProgramme userProgramme = userProgrammeRepository.findById(userProgrammeId)
            .orElseThrow(() -> new RuntimeException("Programme utilisateur non trouvé"));
        
        if (!userProgramme.getUser().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer ce programme");
        }
        
        // Supprimer toutes les progressions associées
        List<ProgressionJournaliere> progressions = progressionRepository.findByUserProgrammeId(userProgrammeId);
        if (!progressions.isEmpty()) {
            progressionRepository.deleteAll(progressions);
        }
        
        // Supprimer le programme utilisateur
        userProgrammeRepository.delete(userProgramme);
    }
    
    private void verifierEtAttribuerBadge(Long userId, String nomBadge, String titre, 
                                         String description, String icone) {
        if (!badgeRepository.existsByUserIdAndNom(userId, nomBadge)) {
            User user = userRepository.findById(userId).orElseThrow();
            Badge badge = new Badge(user, nomBadge, titre, description, icone);
            badgeRepository.save(badge);
        }
    }
}
