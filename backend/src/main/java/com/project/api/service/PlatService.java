package com.project.api.service;

import com.project.api.entity.Plat;
import com.project.api.repository.PlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatService {
    
    private final PlatRepository platRepository;
    
    @Autowired
    public PlatService(PlatRepository platRepository) {
        this.platRepository = platRepository;
    }
    
    public List<Plat> getAllPlats() {
        return platRepository.findAll();
    }
    
    public Plat getPlatById(Long id) {
        return platRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plat non trouvé avec l'id: " + id));
    }
    
    public List<Plat> getPlatsByCategorie(String categorie) {
        return platRepository.findByCategorie(categorie);
    }
    
    public Plat createPlat(Plat plat) {
        return platRepository.save(plat);
    }
    
    public Plat updatePlat(Long id, Plat platDetails) {
        Plat plat = getPlatById(id);
        plat.setNom(platDetails.getNom());
        plat.setDescription(platDetails.getDescription());
        plat.setIngredients(platDetails.getIngredients());
        plat.setCalories(platDetails.getCalories());
        plat.setCategorie(platDetails.getCategorie());
        plat.setImageUrl(platDetails.getImageUrl());
        plat.setTempsPreparation(platDetails.getTempsPreparation());
        return platRepository.save(plat);
    }
    
    public void deletePlat(Long id) {
        platRepository.deleteById(id);
    }
}
