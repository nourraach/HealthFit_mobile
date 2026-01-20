package com.project.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class UserProgrammeRequest {
    
    @NotNull(message = "L'ID du programme est obligatoire")
    private Long programmeId;
    
    private Double poidsDebut;
    
    private Double poidsObjectif;
    
    private LocalDate dateDebut;
    
    // Constructeurs
    public UserProgrammeRequest() {
    }
    
    public UserProgrammeRequest(Long programmeId, Double poidsDebut, Double poidsObjectif, LocalDate dateDebut) {
        this.programmeId = programmeId;
        this.poidsDebut = poidsDebut;
        this.poidsObjectif = poidsObjectif;
        this.dateDebut = dateDebut;
    }
    
    // Getters
    public Long getProgrammeId() {
        return programmeId;
    }
    
    public Double getPoidsDebut() {
        return poidsDebut;
    }
    
    public Double getPoidsObjectif() {
        return poidsObjectif;
    }
    
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    
    // Setters
    public void setProgrammeId(Long programmeId) {
        this.programmeId = programmeId;
    }
    
    public void setPoidsDebut(Double poidsDebut) {
        this.poidsDebut = poidsDebut;
    }
    
    public void setPoidsObjectif(Double poidsObjectif) {
        this.poidsObjectif = poidsObjectif;
    }
    
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
}
