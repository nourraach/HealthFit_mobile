package com.project.api.dto;

public class UserProfileResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String numTel;
    private String adresseEmail;
    private String dateNaissance;
    private Double taille;
    private Double poids;
    private String sexe;
    private String objectif;
    private String niveauActivite;
    private Double imc;
    private Integer age;
    
    public UserProfileResponse() {
    }
    
    public UserProfileResponse(Long id, String nom, String prenom, String numTel, String adresseEmail, 
                              String dateNaissance, Double taille, Double poids, String sexe, 
                              String objectif, String niveauActivite, Double imc, Integer age) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.adresseEmail = adresseEmail;
        this.dateNaissance = dateNaissance;
        this.taille = taille;
        this.poids = poids;
        this.sexe = sexe;
        this.objectif = objectif;
        this.niveauActivite = niveauActivite;
        this.imc = imc;
        this.age = age;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public String getDateNaissance() {
        return dateNaissance;
    }
    
    public void setDateNaissance(String dateNaissance) {
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
    
    public Double getImc() {
        return imc;
    }
    
    public void setImc(Double imc) {
        this.imc = imc;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
}
