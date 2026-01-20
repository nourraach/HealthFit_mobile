package com.project.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "progression_journaliere",
       uniqueConstraints = @UniqueConstraint(
           columnNames = {"user_programme_id", "date"}
       ))
public class ProgressionJournaliere {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_programme_id", nullable = false)
    @JsonIgnoreProperties({"progressions", "user"})
    private UserProgramme userProgramme;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private Integer jourProgramme; // Ex: Jour 5 sur 30
    
    @ManyToMany
    @JoinTable(
        name = "progression_plats_consommes",
        joinColumns = @JoinColumn(name = "progression_id"),
        inverseJoinColumns = @JoinColumn(name = "plat_id")
    )
    private List<Plat> platsConsommes = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "progression_activites_realisees",
        joinColumns = @JoinColumn(name = "progression_id"),
        inverseJoinColumns = @JoinColumn(name = "activite_id")
    )
    private List<ActiviteSportive> activitesRealisees = new ArrayList<>();
    
    @Column
    private Double poidsJour;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(nullable = false)
    private String statutJour; // COMPLETE, PARTIEL, NON_FAIT
    
    @Column
    private Integer scoreJour; // 0-100
    
    @Column
    private Integer caloriesConsommees;
    
    // Constructeurs
    public ProgressionJournaliere() {
    }
    
    public ProgressionJournaliere(UserProgramme userProgramme, LocalDate date, Integer jourProgramme) {
        this.userProgramme = userProgramme;
        this.date = date;
        this.jourProgramme = jourProgramme;
        this.statutJour = "NON_FAIT";
        this.scoreJour = 0;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public UserProgramme getUserProgramme() {
        return userProgramme;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public Integer getJourProgramme() {
        return jourProgramme;
    }
    
    public List<Plat> getPlatsConsommes() {
        return platsConsommes;
    }
    
    public List<ActiviteSportive> getActivitesRealisees() {
        return activitesRealisees;
    }
    
    public Double getPoidsJour() {
        return poidsJour;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public String getStatutJour() {
        return statutJour;
    }
    
    public Integer getScoreJour() {
        return scoreJour;
    }
    
    public Integer getCaloriesConsommees() {
        return caloriesConsommees;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setUserProgramme(UserProgramme userProgramme) {
        this.userProgramme = userProgramme;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public void setJourProgramme(Integer jourProgramme) {
        this.jourProgramme = jourProgramme;
    }
    
    public void setPlatsConsommes(List<Plat> platsConsommes) {
        this.platsConsommes = platsConsommes;
    }
    
    public void setActivitesRealisees(List<ActiviteSportive> activitesRealisees) {
        this.activitesRealisees = activitesRealisees;
    }
    
    public void setPoidsJour(Double poidsJour) {
        this.poidsJour = poidsJour;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public void setStatutJour(String statutJour) {
        this.statutJour = statutJour;
    }
    
    public void setScoreJour(Integer scoreJour) {
        this.scoreJour = scoreJour;
    }
    
    public void setCaloriesConsommees(Integer caloriesConsommees) {
        this.caloriesConsommees = caloriesConsommees;
    }
    
    // Méthode pour calculer les calories
    public void calculerCalories() {
        if (platsConsommes != null && !platsConsommes.isEmpty()) {
            this.caloriesConsommees = platsConsommes.stream()
                .mapToInt(Plat::getCalories)
                .sum();
        }
    }
}
