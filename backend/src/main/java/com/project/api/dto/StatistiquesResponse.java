package com.project.api.dto;

import java.util.List;

public class StatistiquesResponse {
    
    private Integer progressionGlobale; // 0-100%
    private Integer tauxCompletion;
    private Integer tauxRepas;
    private Integer tauxActivites;
    private Integer evolutionPhysique;
    
    private Integer streakActuel;
    private Integer meilleurStreak;
    private Integer joursActifs;
    
    private Integer jourActuel;
    private Integer joursTotal;
    private Integer joursRestants;
    
    private Double poidsDebut;
    private Double poidsActuel;
    private Double poidsObjectif;
    private Double evolutionPoids;
    
    private Integer caloriesMoyennes;
    private Integer totalPlatsConsommes;
    private Integer totalActivitesRealisees;
    
    private List<BadgeResponse> badges;
    
    // Constructeurs
    public StatistiquesResponse() {
    }
    
    // Getters et Setters
    public Integer getProgressionGlobale() {
        return progressionGlobale;
    }
    
    public void setProgressionGlobale(Integer progressionGlobale) {
        this.progressionGlobale = progressionGlobale;
    }
    
    public Integer getTauxCompletion() {
        return tauxCompletion;
    }
    
    public void setTauxCompletion(Integer tauxCompletion) {
        this.tauxCompletion = tauxCompletion;
    }
    
    public Integer getTauxRepas() {
        return tauxRepas;
    }
    
    public void setTauxRepas(Integer tauxRepas) {
        this.tauxRepas = tauxRepas;
    }
    
    public Integer getTauxActivites() {
        return tauxActivites;
    }
    
    public void setTauxActivites(Integer tauxActivites) {
        this.tauxActivites = tauxActivites;
    }
    
    public Integer getEvolutionPhysique() {
        return evolutionPhysique;
    }
    
    public void setEvolutionPhysique(Integer evolutionPhysique) {
        this.evolutionPhysique = evolutionPhysique;
    }
    
    public Integer getStreakActuel() {
        return streakActuel;
    }
    
    public void setStreakActuel(Integer streakActuel) {
        this.streakActuel = streakActuel;
    }
    
    public Integer getMeilleurStreak() {
        return meilleurStreak;
    }
    
    public void setMeilleurStreak(Integer meilleurStreak) {
        this.meilleurStreak = meilleurStreak;
    }
    
    public Integer getJoursActifs() {
        return joursActifs;
    }
    
    public void setJoursActifs(Integer joursActifs) {
        this.joursActifs = joursActifs;
    }
    
    public Integer getJourActuel() {
        return jourActuel;
    }
    
    public void setJourActuel(Integer jourActuel) {
        this.jourActuel = jourActuel;
    }
    
    public Integer getJoursTotal() {
        return joursTotal;
    }
    
    public void setJoursTotal(Integer joursTotal) {
        this.joursTotal = joursTotal;
    }
    
    public Integer getJoursRestants() {
        return joursRestants;
    }
    
    public void setJoursRestants(Integer joursRestants) {
        this.joursRestants = joursRestants;
    }
    
    public Double getPoidsDebut() {
        return poidsDebut;
    }
    
    public void setPoidsDebut(Double poidsDebut) {
        this.poidsDebut = poidsDebut;
    }
    
    public Double getPoidsActuel() {
        return poidsActuel;
    }
    
    public void setPoidsActuel(Double poidsActuel) {
        this.poidsActuel = poidsActuel;
    }
    
    public Double getPoidsObjectif() {
        return poidsObjectif;
    }
    
    public void setPoidsObjectif(Double poidsObjectif) {
        this.poidsObjectif = poidsObjectif;
    }
    
    public Double getEvolutionPoids() {
        return evolutionPoids;
    }
    
    public void setEvolutionPoids(Double evolutionPoids) {
        this.evolutionPoids = evolutionPoids;
    }
    
    public Integer getCaloriesMoyennes() {
        return caloriesMoyennes;
    }
    
    public void setCaloriesMoyennes(Integer caloriesMoyennes) {
        this.caloriesMoyennes = caloriesMoyennes;
    }
    
    public Integer getTotalPlatsConsommes() {
        return totalPlatsConsommes;
    }
    
    public void setTotalPlatsConsommes(Integer totalPlatsConsommes) {
        this.totalPlatsConsommes = totalPlatsConsommes;
    }
    
    public Integer getTotalActivitesRealisees() {
        return totalActivitesRealisees;
    }
    
    public void setTotalActivitesRealisees(Integer totalActivitesRealisees) {
        this.totalActivitesRealisees = totalActivitesRealisees;
    }
    
    public List<BadgeResponse> getBadges() {
        return badges;
    }
    
    public void setBadges(List<BadgeResponse> badges) {
        this.badges = badges;
    }
}
