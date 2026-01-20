package com.project.api.service;

import com.project.api.dto.FavoriPlatResponse;
import com.project.api.dto.FavoriProgrammeResponse;
import com.project.api.dto.FavoriResponse;
import com.project.api.entity.*;
import com.project.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavoriService {
    
    @Autowired
    private FavoriProgrammeRepository favoriProgrammeRepository;
    
    @Autowired
    private FavoriPlatRepository favoriPlatRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProgrammeRepository programmeRepository;
    
    @Autowired
    private PlatRepository platRepository;
    
    // ==================== FAVORIS PROGRAMMES ====================
    
    public FavoriResponse toggleFavoriProgramme(Long userId, Long programmeId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        Programme programme = programmeRepository.findById(programmeId)
            .orElseThrow(() -> new RuntimeException("Programme non trouvé"));
        
        boolean exists = favoriProgrammeRepository.existsByUserIdAndProgrammeId(userId, programmeId);
        
        if (exists) {
            // Retirer des favoris
            favoriProgrammeRepository.deleteByUserIdAndProgrammeId(userId, programmeId);
            Long totalFavorites = favoriProgrammeRepository.countByProgrammeId(programmeId);
            return new FavoriResponse(true, "Programme retiré des favoris", false, totalFavorites);
        } else {
            // Ajouter aux favoris
            FavoriProgramme favori = new FavoriProgramme(user, programme);
            favoriProgrammeRepository.save(favori);
            Long totalFavorites = favoriProgrammeRepository.countByProgrammeId(programmeId);
            return new FavoriResponse(true, "Programme ajouté aux favoris", true, totalFavorites);
        }
    }
    
    public boolean isProgrammeFavorite(Long userId, Long programmeId) {
        return favoriProgrammeRepository.existsByUserIdAndProgrammeId(userId, programmeId);
    }
    
    public Page<FavoriProgrammeResponse> getFavorisProgrammes(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FavoriProgramme> favoris = favoriProgrammeRepository.findByUserIdOrderByDateAjoutDesc(userId, pageable);
        
        return favoris.map(this::convertToFavoriProgrammeResponse);
    }
    
    public List<FavoriProgrammeResponse> getAllFavorisProgrammes(Long userId) {
        List<FavoriProgramme> favoris = favoriProgrammeRepository.findByUserIdOrderByDateAjoutDesc(userId);
        
        return favoris.stream()
            .map(this::convertToFavoriProgrammeResponse)
            .collect(Collectors.toList());
    }
    
    public Long countFavorisProgrammes(Long userId) {
        return favoriProgrammeRepository.countByUserId(userId);
    }
    
    // ==================== FAVORIS PLATS ====================
    
    public FavoriResponse toggleFavoriPlat(Long userId, Long platId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        Plat plat = platRepository.findById(platId)
            .orElseThrow(() -> new RuntimeException("Plat non trouvé"));
        
        boolean exists = favoriPlatRepository.existsByUserIdAndPlatId(userId, platId);
        
        if (exists) {
            // Retirer des favoris
            favoriPlatRepository.deleteByUserIdAndPlatId(userId, platId);
            Long totalFavorites = favoriPlatRepository.countByPlatId(platId);
            return new FavoriResponse(true, "Plat retiré des favoris", false, totalFavorites);
        } else {
            // Ajouter aux favoris
            FavoriPlat favori = new FavoriPlat(user, plat);
            favoriPlatRepository.save(favori);
            Long totalFavorites = favoriPlatRepository.countByPlatId(platId);
            return new FavoriResponse(true, "Plat ajouté aux favoris", true, totalFavorites);
        }
    }
    
    public boolean isPlatFavorite(Long userId, Long platId) {
        return favoriPlatRepository.existsByUserIdAndPlatId(userId, platId);
    }
    
    public Page<FavoriPlatResponse> getFavorisPlats(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FavoriPlat> favoris = favoriPlatRepository.findByUserIdOrderByDateAjoutDesc(userId, pageable);
        
        return favoris.map(this::convertToFavoriPlatResponse);
    }
    
    public List<FavoriPlatResponse> getAllFavorisPlats(Long userId) {
        List<FavoriPlat> favoris = favoriPlatRepository.findByUserIdOrderByDateAjoutDesc(userId);
        
        return favoris.stream()
            .map(this::convertToFavoriPlatResponse)
            .collect(Collectors.toList());
    }
    
    public Long countFavorisPlats(Long userId) {
        return favoriPlatRepository.countByUserId(userId);
    }
    
    // ==================== MÉTHODES DE CONVERSION ====================
    
    private FavoriProgrammeResponse convertToFavoriProgrammeResponse(FavoriProgramme favori) {
        FavoriProgrammeResponse response = new FavoriProgrammeResponse();
        
        response.setId(favori.getId());
        response.setProgrammeId(favori.getProgramme().getId());
        response.setNom(favori.getProgramme().getNom());
        response.setDescription(favori.getProgramme().getDescription());
        response.setDureeJours(favori.getProgramme().getDureeJours());
        response.setObjectif(favori.getProgramme().getObjectif());
        response.setImageUrl(favori.getProgramme().getImageUrl());
        response.setDateAjout(favori.getDateAjout());
        response.setFavorite(true);
        
        return response;
    }
    
    private FavoriPlatResponse convertToFavoriPlatResponse(FavoriPlat favori) {
        FavoriPlatResponse response = new FavoriPlatResponse();
        
        response.setId(favori.getId());
        response.setPlatId(favori.getPlat().getId());
        response.setNom(favori.getPlat().getNom());
        response.setDescription(favori.getPlat().getDescription());
        response.setCalories(favori.getPlat().getCalories());
        response.setImageUrl(favori.getPlat().getImageUrl());
        response.setTypePlat(favori.getPlat().getCategorie()); // Utiliser getCategorie() au lieu de getTypePlat()
        response.setDateAjout(favori.getDateAjout());
        response.setFavorite(true);
        
        return response;
    }
}