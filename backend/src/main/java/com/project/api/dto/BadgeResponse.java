package com.project.api.dto;

import java.time.LocalDateTime;

public class BadgeResponse {
    
    private Long id;
    private String nom;
    private String titre;
    private String description;
    private String icone;
    private LocalDateTime dateObtention;
    
    // Constructeurs
    public BadgeResponse() {
    }
    
    public BadgeResponse(Long id, String nom, String titre, String description, String icone, LocalDateTime dateObtention) {
        this.id = id;
        this.nom = nom;
        this.titre = titre;
        this.description = description;
        this.icone = icone;
        this.dateObtention = dateObtention;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getIcone() {
        return icone;
    }
    
    public void setIcone(String icone) {
        this.icone = icone;
    }
    
    public LocalDateTime getDateObtention() {
        return dateObtention;
    }
    
    public void setDateObtention(LocalDateTime dateObtention) {
        this.dateObtention = dateObtention;
    }
}
