package com.project.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "activites_sportives")
public class ActiviteSportive {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private Integer dureeMinutes;
    
    @Column(nullable = false)
    private Integer caloriesBrulees;
    
    @Column(nullable = false)
    private String niveau; // DEBUTANT, INTERMEDIAIRE, AVANCE
    
    @Column(nullable = false)
    private String type; // CARDIO, MUSCULATION, YOGA, STRETCHING, HIIT
    
    @Column
    private String videoUrl;
    
    @Column
    private String imageUrl;
    
    // Constructeurs
    public ActiviteSportive() {
    }
    
    public ActiviteSportive(String nom, String description, Integer dureeMinutes, Integer caloriesBrulees, 
                           String niveau, String type) {
        this.nom = nom;
        this.description = description;
        this.dureeMinutes = dureeMinutes;
        this.caloriesBrulees = caloriesBrulees;
        this.niveau = niveau;
        this.type = type;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Integer getDureeMinutes() {
        return dureeMinutes;
    }
    
    public Integer getCaloriesBrulees() {
        return caloriesBrulees;
    }
    
    public String getNiveau() {
        return niveau;
    }
    
    public String getType() {
        return type;
    }
    
    public String getVideoUrl() {
        return videoUrl;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setDureeMinutes(Integer dureeMinutes) {
        this.dureeMinutes = dureeMinutes;
    }
    
    public void setCaloriesBrulees(Integer caloriesBrulees) {
        this.caloriesBrulees = caloriesBrulees;
    }
    
    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
