package com.project.api.dto;

public class AuthenticationResponse {
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String nom;
    private String prenom;
    private String adresseEmail;
    
    // Constructeurs
    public AuthenticationResponse() {
    }
    
    public AuthenticationResponse(String token, Long userId, String nom, String prenom, String adresseEmail) {
        this.token = token;
        this.type = "Bearer";
        this.userId = userId;
        this.nom = nom;
        this.prenom = prenom;
        this.adresseEmail = adresseEmail;
    }
    
    // Getters
    public String getToken() {
        return token;
    }
    
    public String getType() {
        return type;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public String getAdresseEmail() {
        return adresseEmail;
    }
    
    // Setters
    public void setToken(String token) {
        this.token = token;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }
}
