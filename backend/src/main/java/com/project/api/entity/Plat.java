package com.project.api.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plats")
public class Plat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(length = 2000)
    private String description;
    
    @ElementCollection
    @CollectionTable(name = "plat_ingredients", joinColumns = @JoinColumn(name = "plat_id"))
    @Column(name = "ingredient")
    private List<String> ingredients = new ArrayList<>();
    
    @Column(nullable = false)
    private Integer calories;
    
    @Column(nullable = false)
    private String categorie; // petit-dejeuner, dejeuner, diner, collation
    
    private String imageUrl;
    
    @Column(nullable = false)
    private Integer tempsPreparation; // en minutes
    
    // Constructeurs
    public Plat() {
    }
    
    public Plat(Long id, String nom, String description, List<String> ingredients, Integer calories, 
                String categorie, String imageUrl, Integer tempsPreparation) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.ingredients = ingredients;
        this.calories = calories;
        this.categorie = categorie;
        this.imageUrl = imageUrl;
        this.tempsPreparation = tempsPreparation;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getDescription() {
        return description;
    }
    
    public List<String> getIngredients() {
        return ingredients;
    }
    
    public Integer getCalories() {
        return calories;
    }
    
    public String getCategorie() {
        return categorie;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public Integer getTempsPreparation() {
        return tempsPreparation;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    
    public void setCalories(Integer calories) {
        this.calories = calories;
    }
    
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public void setTempsPreparation(Integer tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }
}
