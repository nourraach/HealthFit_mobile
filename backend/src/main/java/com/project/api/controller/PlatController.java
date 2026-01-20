package com.project.api.controller;

import com.project.api.entity.Plat;
import com.project.api.service.PlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plats")
@CrossOrigin(origins = "*")
public class PlatController {
    
    private final PlatService platService;
    
    @Autowired
    public PlatController(PlatService platService) {
        this.platService = platService;
    }
    
    @GetMapping
    public ResponseEntity<List<Plat>> getAllPlats() {
        return ResponseEntity.ok(platService.getAllPlats());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Plat> getPlatById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(platService.getPlatById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<Plat>> getPlatsByCategorie(@PathVariable String categorie) {
        return ResponseEntity.ok(platService.getPlatsByCategorie(categorie));
    }
    
    @PostMapping
    public ResponseEntity<Plat> createPlat(@RequestBody Plat plat) {
        return ResponseEntity.status(HttpStatus.CREATED).body(platService.createPlat(plat));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Plat> updatePlat(@PathVariable Long id, @RequestBody Plat plat) {
        try {
            return ResponseEntity.ok(platService.updatePlat(id, plat));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlat(@PathVariable Long id) {
        platService.deletePlat(id);
        return ResponseEntity.noContent().build();
    }
}
