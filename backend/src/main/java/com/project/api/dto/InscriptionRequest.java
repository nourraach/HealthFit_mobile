package com.project.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public class InscriptionRequest {
    
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;
    
    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String numTel;
    
    @NotBlank(message = "L'adresse email est obligatoire")
    @Email(message = "L'adresse email doit être valide")
    private String adresseEmail;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
    
    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;
    
    private Double taille;
    private Double poids;
    private String sexe;
    private String objectif;
    private String niveauActivite;
    
    // Constructeurs
    public InscriptionRequest() {
    }
    
    public InscriptionRequest(String nom, String prenom, String numTel, String adresseEmail, String motDePasse, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.adresseEmail = adresseEmail;
        this.motDePasse = motDePasse;
        this.dateNaissance = dateNaissance;
    }
    
    // Getters
    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public String getNumTel() {
        return numTel;
    }
    
    public String getAdresseEmail() {
        return adresseEmail;
    }
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    
    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }
    
    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
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
