package com.project.api.controller;

import com.project.api.dto.MessageResponse;
import com.project.api.dto.ProgressionJournaliereRequest;
import com.project.api.entity.ProgressionJournaliere;
import com.project.api.service.ProgressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/progression")
@CrossOrigin(origins = "*")
public class ProgressionController {
    
    private final ProgressionService progressionService;
    
    @Autowired
    public ProgressionController(ProgressionService progressionService) {
        this.progressionService = progressionService;
    }
    
    @PostMapping("/enregistrer")
    public ResponseEntity<?> enregistrerProgression(
            @RequestBody ProgressionJournaliereRequest request) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            
            ProgressionJournaliere progression;
            if (request.getUserProgrammeId() != null) {
                // Record progression for specific user program
                progression = progressionService.enregistrerProgressionForProgram(userId, request, request.getUserProgrammeId());
            } else {
                // Fallback to current active program (backward compatibility)
                progression = progressionService.enregistrerProgression(userId, request);
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(progression);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/historique")
    public ResponseEntity<?> getHistoriqueProgression() {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            List<ProgressionJournaliere> progressions = progressionService.getHistoriqueProgression(userId);
            return ResponseEntity.ok(progressions);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/aujourd-hui")
    public ResponseEntity<?> getProgressionDuJour() {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            ProgressionJournaliere progression = progressionService.getProgressionDuJour(userId);
            if (progression == null) {
                return ResponseEntity.ok(new MessageResponse("Aucune progression enregistrée aujourd'hui"));
            }
            return ResponseEntity.ok(progression);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/date")
    public ResponseEntity<?> getProgressionByDate(
            @RequestParam("date") String dateStr,
            @RequestParam(value = "userProgrammeId", required = false) Long userProgrammeId) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            LocalDate date = LocalDate.parse(dateStr);
            
            ProgressionJournaliere progression;
            if (userProgrammeId != null) {
                // Get progression for specific user program
                progression = progressionService.getProgressionByDateAndProgram(userId, date, userProgrammeId);
            } else {
                // Fallback to current active program (backward compatibility)
                progression = progressionService.getProgressionByDate(userId, date);
            }
            
            if (progression == null) {
                return ResponseEntity.ok(new MessageResponse("Aucune progression enregistrée pour cette date"));
            }
            return ResponseEntity.ok(progression);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
