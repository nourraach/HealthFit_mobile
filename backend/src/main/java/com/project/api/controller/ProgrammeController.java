package com.project.api.controller;

//Ajoutez ces imports en haut du fichier
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.dto.ErroroResponse;
import com.project.api.dto.MessageResponse;
import com.project.api.dto.StatistiquesResponse;
import com.project.api.dto.UserProgrammeRequest;
import com.project.api.entity.Programme;
import com.project.api.entity.UserProgramme;
import com.project.api.exception.ResourceNotFoundException;
import com.project.api.service.ProgrammeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/programmes")
@CrossOrigin(origins = "*")
public class ProgrammeController {
    
    private final ProgrammeService programmeService;
    
    @Autowired
    public ProgrammeController(ProgrammeService programmeService) {
        this.programmeService = programmeService;
    }
    
    @PostMapping("/assigner")
    public ResponseEntity<?> assignerProgramme(@Valid @RequestBody UserProgrammeRequest request) {
        Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
        UserProgramme userProgramme = programmeService.assignerProgramme(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userProgramme);
    }
    
    @GetMapping("/actif")
    public ResponseEntity<?> getProgrammeActif() {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            UserProgramme userProgramme = programmeService.getProgrammeActif(userId);
            return ResponseEntity.ok(userProgramme);
        } catch (Exception e) {
            System.err.println("Error in getProgrammeActif: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Une erreur interne s'est produite: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getProgrammeById(@PathVariable Long id) {
        try {
            Programme programme = programmeService.getProgrammeById(id);
            return ResponseEntity.ok(programme);
        } catch (ResourceNotFoundException e) {
            // Version corrigée
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErroroResponse(
                            LocalDateTime.now(),
                            HttpStatus.NOT_FOUND.value(),
                            "Not Found",
                            e.getMessage(),
                            "/api/programmes/" + id
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErroroResponse(
                            LocalDateTime.now(),
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error",
                            "Erreur lors de la récupération du programme: " + e.getMessage(),
                            "/api/programmes/" + id
                    ));
        }
    }
    
    @GetMapping("/historique")
    public ResponseEntity<?> getHistoriqueProgrammes() {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            System.out.println("=== DEBUG MES PROGRAMMES ===");
            System.out.println("User ID from JWT: " + userId);
            
            List<UserProgramme> programmes = programmeService.getHistoriqueProgrammes(userId);
            System.out.println("Programmes trouvés: " + programmes.size());
            
            for (int i = 0; i < programmes.size(); i++) {
                UserProgramme up = programmes.get(i);
                System.out.println("Programme " + (i+1) + ": ID=" + up.getId() + 
                    ", Nom=" + (up.getProgramme() != null ? up.getProgramme().getNom() : "NULL") + 
                    ", Statut=" + up.getStatut());
            }
            System.out.println("=============================");
            
            return ResponseEntity.ok(programmes);
        } catch (Exception e) {
            // ✅ BULLETPROOF FALLBACK: If all else fails, return basic programmes
            System.err.println("Error in getHistoriqueProgrammes: " + e.getMessage());
            e.printStackTrace();
            
            try {
                // Last resort: return basic user programmes without complete data
                Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
                System.out.println("=== FALLBACK DEBUG ===");
                System.out.println("Using fallback method for User ID: " + userId);
                
                List<UserProgramme> basicProgrammes = programmeService.getBasicHistoriqueProgrammes(userId);
                System.out.println("Basic programmes trouvés: " + basicProgrammes.size());
                System.out.println("======================");
                
                return ResponseEntity.ok(basicProgrammes);
            } catch (Exception fallbackError) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Une erreur interne s'est produite: " + e.getMessage()));
            }
        }
    }
    
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserProgrammeById(@PathVariable Long id) {
        try {
            UserProgramme userProgramme = programmeService.getUserProgrammeById(id);
            return ResponseEntity.ok(userProgramme);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/statistiques")
    public ResponseEntity<?> getStatistiques() {
        Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
        StatistiquesResponse stats = programmeService.getStatistiques(userId);
        return ResponseEntity.ok(stats);
    }
    
    @PutMapping("/terminer")
    public ResponseEntity<?> terminerProgramme() {
        Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
        programmeService.terminerProgramme(userId);
        return ResponseEntity.ok(new MessageResponse("Programme terminé avec succès"));
    }
    
    @PutMapping("/pauser")
    public ResponseEntity<?> pauserProgramme() {
        Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
        programmeService.pauserProgramme(userId);
        return ResponseEntity.ok(new MessageResponse("Programme mis en pause"));
    }
    
    @PutMapping("/reprendre")
    public ResponseEntity<?> reprendreProgramme() {
        Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
        programmeService.reprendreProgramme(userId);
        return ResponseEntity.ok(new MessageResponse("Programme repris"));
    }
    
    @PostMapping("/user/{id}/supprimer")
    public ResponseEntity<?> supprimerUserProgramme(@PathVariable Long id) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            programmeService.supprimerUserProgramme(userId, id);
            return ResponseEntity.ok(new MessageResponse("Programme supprimé avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Erreur lors de la suppression: " + e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllProgrammes(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            org.springframework.security.core.Authentication authentication) {
        try {
            System.out.println("=== ProgrammeController.getAllProgrammes ===");
            System.out.println("Authorization header: " + authHeader);
            System.out.println("Authentication object: " + authentication);
            if (authentication != null) {
                System.out.println("User: " + authentication.getName());
                System.out.println("Authorities: " + authentication.getAuthorities());
                System.out.println("Is authenticated: " + authentication.isAuthenticated());
            } else {
                System.out.println("Authentication is NULL!");
            }
            System.out.println("==========================================");
            
            List<Programme> programmes = programmeService.getAllProgrammes();
            return ResponseEntity.ok(programmes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    

}
