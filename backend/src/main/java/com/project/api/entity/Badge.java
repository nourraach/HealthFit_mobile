package com.project.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "badges")
public class Badge {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"motDePasse", "badges", "programmes"})
    private User user;
    
    @Column(nullable = false)
    private String nom; // DEBUTANT, REGULIER, CHAMPION, SPORTIF, NUTRITIONNISTE, OBJECTIF_ATTEINT
    
    @Column(nullable = false)
    private String titre;
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false)
    private LocalDateTime dateObtention;
    
    @Column
    private String icone; // emoji ou URL
    
    // Constructeurs
    public Badge() {
    }
    
    public Badge(User user, String nom, String titre, String description, String icone) {
        this.user = user;
        this.nom = nom;
        this.titre = titre;
        this.description = description;
        this.icone = icone;
        this.dateObtention = LocalDateTime.now();
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public User getUser() {
        return user;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public String getDescription() {
        return description;
    }
    
    public LocalDateTime getDateObtention() {
        return dateObtention;
    }
    
    public String getIcone() {
        return icone;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setDateObtention(LocalDateTime dateObtention) {
        this.dateObtention = dateObtention;
    }
    
    public void setIcone(String icone) {
        this.icone = icone;
    }
}
