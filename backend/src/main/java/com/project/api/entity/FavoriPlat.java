package com.project.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favoris_plats", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "plat_id"}))
public class FavoriPlat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plat_id", nullable = false)
    private Plat plat;
    
    @Column(name = "date_ajout", nullable = false)
    private LocalDateTime dateAjout;
    
    // Constructeurs
    public FavoriPlat() {
        this.dateAjout = LocalDateTime.now();
    }
    
    public FavoriPlat(User user, Plat plat) {
        this();
        this.user = user;
        this.plat = plat;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Plat getPlat() { return plat; }
    public void setPlat(Plat plat) { this.plat = plat; }
    
    public LocalDateTime getDateAjout() { return dateAjout; }
    public void setDateAjout(LocalDateTime dateAjout) { this.dateAjout = dateAjout; }
}