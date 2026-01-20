package com.project.api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "programmes")
public class Programme {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(length = 2000)
    private String description;
    
    @Column(nullable = false)
    private Integer dureeJours; // durée du programme en jours
    
    @Column(nullable = false)
    private String objectif; // perte-poids, prise-masse, maintien, endurance
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "programme_plats",
        joinColumns = @JoinColumn(name = "programme_id"),
        inverseJoinColumns = @JoinColumn(name = "plat_id")
    )
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private List<Plat> plats = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "programme_activites",
        joinColumns = @JoinColumn(name = "programme_id"),
        inverseJoinColumns = @JoinColumn(name = "activite_id")
    )
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    private List<ActiviteSportive> activites = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "programme_conseils", joinColumns = @JoinColumn(name = "programme_id"))
    @Column(name = "conseil", length = 1000)
    private List<String> conseils = new ArrayList<>();
    
    private String imageUrl;
    
    // Constructeurs
    public Programme() {
    }
    
    public Programme(Long id, String nom, String description, Integer dureeJours, String objectif, 
                    List<Plat> plats, List<ActiviteSportive> activites, List<String> conseils, String imageUrl) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dureeJours = dureeJours;
        this.objectif = objectif;
        this.plats = plats;
        this.activites = activites;
        this.conseils = conseils;
        this.imageUrl = imageUrl;
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
    
    public Integer getDureeJours() {
        return dureeJours;
    }
    
    public String getObjectif() {
        return objectif;
    }
    
    public List<Plat> getPlats() {
        return plats;
    }
    
    public List<ActiviteSportive> getActivites() {
        return activites;
    }
    
    public List<String> getConseils() {
        return conseils;
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
    
    public void setDureeJours(Integer dureeJours) {
        this.dureeJours = dureeJours;
    }
    
    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }
    
    public void setPlats(List<Plat> plats) {
        this.plats = plats;
    }
    
    public void setActivites(List<ActiviteSportive> activites) {
        this.activites = activites;
    }
    
    public void setConseils(List<String> conseils) {
        this.conseils = conseils;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
