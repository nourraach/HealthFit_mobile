package com.project.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthenticationRequest {
    
    @NotBlank(message = "L'adresse email est obligatoire")
    @Email(message = "L'adresse email doit être valide")
    private String adresseEmail;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
    
    // Constructeurs
    public AuthenticationRequest() {
    }
    
    public AuthenticationRequest(String adresseEmail, String motDePasse) {
        this.adresseEmail = adresseEmail;
        this.motDePasse = motDePasse;
    }
    
    // Getters
    public String getAdresseEmail() {
        return adresseEmail;
    }
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    // Setters
    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
