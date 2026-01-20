package com.project.api.dto;

import java.time.LocalDate;
import java.util.List;

public class ProgressionJournaliereRequest {
    
    private LocalDate date;
    private List<Long> platIds;
    private List<Long> activiteIds;
    private Double poidsJour;
    private String notes;
    private Long userProgrammeId; // New field for specific program targeting
    
    // Constructeurs
    public ProgressionJournaliereRequest() {
    }
    
    // Getters
    public LocalDate getDate() {
        return date;
    }
    
    public List<Long> getPlatIds() {
        return platIds;
    }
    
    public List<Long> getActiviteIds() {
        return activiteIds;
    }
    
    public Double getPoidsJour() {
        return poidsJour;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public Long getUserProgrammeId() {
        return userProgrammeId;
    }
    
    // Setters
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public void setPlatIds(List<Long> platIds) {
        this.platIds = platIds;
    }
    
    public void setActiviteIds(List<Long> activiteIds) {
        this.activiteIds = activiteIds;
    }
    
    public void setPoidsJour(Double poidsJour) {
        this.poidsJour = poidsJour;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public void setUserProgrammeId(Long userProgrammeId) {
        this.userProgrammeId = userProgrammeId;
    }
}
