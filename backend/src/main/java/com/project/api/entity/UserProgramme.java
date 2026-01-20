package com.project.api.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_programmes")
public class UserProgramme {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"motDePasse", "programmes"})
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "programme_id", nullable = false)
    private Programme programme;
    
    @Column(nullable = false)
    private LocalDate dateDebut;
    
    @Column(nullable = false)
    private LocalDate dateFinPrevue;
    
    @Column(nullable = false)
    private String statut; // EN_COURS, TERMINE, ABANDONNE, PAUSE
    
    @Column
    private Double poidsDebut;
    
    @Column
    private Double poidsActuel;
    
    @Column
    private Double poidsObjectif;
    
    @Column
    private LocalDate dateFin; // Date réelle de fin
    
    // Constructeurs
    public UserProgramme() {
    }
    
    public UserProgramme(User user, Programme programme, LocalDate dateDebut, Double poidsDebut, Double poidsObjectif) {
        this.user = user;
        this.programme = programme;
        this.dateDebut = dateDebut;
        // Fix: Include the last day of the programme (was excluding it)
        this.dateFinPrevue = dateDebut.plusDays(programme.getDureeJours() - 1);
        this.statut = "EN_COURS";
        this.poidsDebut = poidsDebut;
        this.poidsActuel = poidsDebut;
        this.poidsObjectif = poidsObjectif;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public User getUser() {
        return user;
    }
    
    public Programme getProgramme() {
        return programme;
    }
    
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    
    public LocalDate getDateFinPrevue() {
        return dateFinPrevue;
    }
    
    public String getStatut() {
        return statut;
    }
    
    public Double getPoidsDebut() {
        return poidsDebut;
    }
    
    public Double getPoidsActuel() {
        return poidsActuel;
    }
    
    public Double getPoidsObjectif() {
        return poidsObjectif;
    }
    
    public LocalDate getDateFin() {
        return dateFin;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setProgramme(Programme programme) {
        this.programme = programme;
    }
    
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public void setDateFinPrevue(LocalDate dateFinPrevue) {
        this.dateFinPrevue = dateFinPrevue;
    }
    
    public void setStatut(String statut) {
        this.statut = statut;
    }
    
    public void setPoidsDebut(Double poidsDebut) {
        this.poidsDebut = poidsDebut;
    }
    
    public void setPoidsActuel(Double poidsActuel) {
        this.poidsActuel = poidsActuel;
    }
    
    public void setPoidsObjectif(Double poidsObjectif) {
        this.poidsObjectif = poidsObjectif;
    }
    
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
    
    // Méthodes utiles
    public Integer getJourActuel() {
        if (dateDebut == null) return 1;
        
        LocalDate aujourdhui = LocalDate.now();
        long joursEcoules = ChronoUnit.DAYS.between(dateDebut, aujourdhui) + 1;
        
        // Ne pas dépasser la durée du programme
        return (int) Math.min(joursEcoules, programme.getDureeJours());
    }

    public Integer getJoursRestants() {
        return Math.max(0, programme.getDureeJours() - getJourActuel());
    }
}
