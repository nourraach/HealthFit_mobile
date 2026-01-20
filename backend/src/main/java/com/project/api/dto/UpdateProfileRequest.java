package com.project.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class UpdateProfileRequest {
    
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;
    
    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String numTel;
    
    @NotBlank(message = "L'adresse email est obligatoire")
    @Email(message = "L'adresse email doit être valide")
    private String adresseEmail;
    
    private LocalDate dateNaissance;
    
    @Positive(message = "La taille doit être positive")
    private Double taille;
    
    @Positive(message = "Le poids doit être positif")
    private Double poids;
    
    private String sexe;
    private String objectif;
    private String niveauActivite;
    
    public UpdateProfileRequest() {
    }
    
    // Getters et Setters
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getNumTel() {
        return numTel;
    }
    
    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }
    
    public String getAdresseEmail() {
        return adresseEmail;
    }
    
    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }
    
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
    public Double getTaille() {
        return taille;
    }
    
    public void setTaille(Double taille) {
        this.taille = taille;
    }
    
    public Double getPoids() {
        return poids;
    }
    
    public void setPoids(Double poids) {
        this.poids = poids;
    }
    
    public String getSexe() {
        return sexe;
    }
    
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    
    public String getObjectif() {
        return objectif;
    }
    
    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }
    
    public String getNiveauActivite() {
        return niveauActivite;
    }
    
    public void setNiveauActivite(String niveauActivite) {
        this.niveauActivite = niveauActivite;
    }
}
