package com.project.api.dto;

import java.time.LocalDateTime;

public class FavoriPlatResponse {
    
    private Long id;
    private Long platId;
    private String nom;
    private String description;
    private Integer calories;
    private String imageUrl;
    private String typePlat;
    private LocalDateTime dateAjout;
    private boolean isFavorite;
    
    // Constructeurs
    public FavoriPlatResponse() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPlatId() { return platId; }
    public void setPlatId(Long platId) { this.platId = platId; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getCalories() { return calories; }
    public void setCalories(Integer calories) { this.calories = calories; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    
    public String getTypePlat() { return typePlat; }
    public void setTypePlat(String typePlat) { this.typePlat = typePlat; }
    
    public LocalDateTime getDateAjout() { return dateAjout; }
    public void setDateAjout(LocalDateTime dateAjout) { this.dateAjout = dateAjout; }
    
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}