package com.project.api.controller;

import com.project.api.dto.FavoriPlatResponse;
import com.project.api.dto.FavoriProgrammeResponse;
import com.project.api.dto.FavoriResponse;
import com.project.api.service.FavoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favoris")
@CrossOrigin(origins = "*")
public class FavoriController {
    
    @Autowired
    private FavoriService favoriService;
    
    // ==================== FAVORIS PROGRAMMES ====================
    
    @PostMapping("/programmes/{programmeId}")
    public ResponseEntity<FavoriResponse> toggleFavoriProgramme(@PathVariable Long programmeId) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            FavoriResponse response = favoriService.toggleFavoriProgramme(userId, programmeId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new FavoriResponse(false, "Erreur: " + e.getMessage(), false));
        }
    }
    
    @GetMapping("/programmes/{programmeId}/status")
    public ResponseEntity<Map<String, Object>> getProgrammeFavoriteStatus(@PathVariable Long programmeId) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            boolean isFavorite = favoriService.isProgrammeFavorite(userId, programmeId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("isFavorite", isFavorite);
            response.put("programmeId", programmeId);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/programmes")
    public ResponseEntity<Page<FavoriProgrammeResponse>> getFavorisProgrammes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            Page<FavoriProgrammeResponse> favoris = favoriService.getFavorisProgrammes(userId, page, size);
            return ResponseEntity.ok(favoris);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/programmes/all")
    public ResponseEntity<List<FavoriProgrammeResponse>> getAllFavorisProgrammes() {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            List<FavoriProgrammeResponse> favoris = favoriService.getAllFavorisProgrammes(userId);
            return ResponseEntity.ok(favoris);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // ==================== FAVORIS PLATS ====================
    
    @PostMapping("/plats/{platId}")
    public ResponseEntity<FavoriResponse> toggleFavoriPlat(@PathVariable Long platId) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            FavoriResponse response = favoriService.toggleFavoriPlat(userId, platId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new FavoriResponse(false, "Erreur: " + e.getMessage(), false));
        }
    }
    
    @GetMapping("/plats/{platId}/status")
    public ResponseEntity<Map<String, Object>> getPlatFavoriteStatus(@PathVariable Long platId) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            boolean isFavorite = favoriService.isPlatFavorite(userId, platId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("isFavorite", isFavorite);
            response.put("platId", platId);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/plats")
    public ResponseEntity<Page<FavoriPlatResponse>> getFavorisPlats(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            Page<FavoriPlatResponse> favoris = favoriService.getFavorisPlats(userId, page, size);
            return ResponseEntity.ok(favoris);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/plats/all")
    public ResponseEntity<List<FavoriPlatResponse>> getAllFavorisPlats() {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            List<FavoriPlatResponse> favoris = favoriService.getAllFavorisPlats(userId);
            return ResponseEntity.ok(favoris);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // ==================== STATISTIQUES FAVORIS ====================
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getFavorisStats() {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            
            Long totalProgrammes = favoriService.countFavorisProgrammes(userId);
            Long totalPlats = favoriService.countFavorisPlats(userId);
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalProgrammesFavoris", totalProgrammes);
            stats.put("totalPlatsFavoris", totalPlats);
            stats.put("totalFavoris", totalProgrammes + totalPlats);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}