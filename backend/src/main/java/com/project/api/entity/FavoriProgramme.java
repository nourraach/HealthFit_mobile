package com.project.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favoris_programmes", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "programme_id"}))
public class FavoriProgramme {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programme_id", nullable = false)
    private Programme programme;
    
    @Column(name = "date_ajout", nullable = false)
    private LocalDateTime dateAjout;
    
    // Constructeurs
    public FavoriProgramme() {
        this.dateAjout = LocalDateTime.now();
    }
    
    public FavoriProgramme(User user, Programme programme) {
        this();
        this.user = user;
        this.programme = programme;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Programme getProgramme() { return programme; }
    public void setProgramme(Programme programme) { this.programme = programme; }
    
    public LocalDateTime getDateAjout() { return dateAjout; }
    public void setDateAjout(LocalDateTime dateAjout) { this.dateAjout = dateAjout; }
}