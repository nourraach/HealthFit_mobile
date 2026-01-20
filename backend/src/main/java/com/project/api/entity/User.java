package com.project.api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String prenom;
    
    @Column(nullable = false, unique = true)
    private String numTel;
    
    @Column(nullable = false, unique = true)
    private String adresseEmail;
    
    @Column(nullable = false)
    private String motDePasse;
    
    @Column(nullable = false)
    private LocalDate dateNaissance;
    
    // Caractéristiques physiques
    @Column
    private Double taille; // en mètres (ex: 1.75)
    
    @Column
    private Double poids; // en kg
    
    @Column
    private String sexe; // "HOMME" ou "FEMME"
    
    @Column
    private String objectif; // "PERTE_POIDS", "PRISE_MASSE", "MAINTIEN"
    
    @Column
    private String niveauActivite; // "SEDENTAIRE", "LEGER", "MODERE", "INTENSE", "TRES_INTENSE"
    
    // Constructeurs
    public User() {
    }
    
    public User(Long id, String nom, String prenom, String numTel, String adresseEmail, String motDePasse, LocalDate dateNaissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.adresseEmail = adresseEmail;
        this.motDePasse = motDePasse;
        this.dateNaissance = dateNaissance;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
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
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    // Méthode pour calculer l'IMC
    public Double calculerIMC() {
        if (taille != null && poids != null && taille > 0) {
            return poids / (taille * taille);
        }
        return null;
    }
    
    // Méthode pour calculer l'âge
    public Integer calculerAge() {
        if (dateNaissance != null) {
            return LocalDate.now().getYear() - dateNaissance.getYear();
        }
        return null;
    }
}
