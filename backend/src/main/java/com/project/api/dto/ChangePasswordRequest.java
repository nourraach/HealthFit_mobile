package com.project.api.dto;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordRequest {
    
    @NotBlank(message = "L'ancien mot de passe est obligatoire")
    private String ancienMotDePasse;
    
    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    private String nouveauMotDePasse;
    
    public ChangePasswordRequest() {
    }
    
    public ChangePasswordRequest(String ancienMotDePasse, String nouveauMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
        this.nouveauMotDePasse = nouveauMotDePasse;
    }
    
    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }
    
    public void setAncienMotDePasse(String ancienMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
    }
    
    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }
    
    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }
}
