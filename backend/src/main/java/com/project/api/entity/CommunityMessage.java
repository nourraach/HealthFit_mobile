package com.project.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "community_messages")
public class CommunityMessage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;
    
    @Column(nullable = false)
    private Boolean anonyme = false;
    
    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User auteur;
    
    @Column(name = "nom_anonyme")
    private String nomAnonyme;
    
    @Column(name = "likes_count")
    private Integer likesCount = 0;
    
    @Column(name = "replies_count")
    private Integer repliesCount = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_message_id")
    private CommunityMessage parentMessage;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    
    // Constructeurs
    public CommunityMessage() {
        this.dateCreation = LocalDateTime.now();
    }
    
    public CommunityMessage(String contenu, User auteur, Boolean anonyme) {
        this();
        this.contenu = contenu;
        this.auteur = auteur;
        this.anonyme = anonyme;
        if (anonyme) {
            this.nomAnonyme = "Utilisateur" + (auteur.getId() % 1000);
        }
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { 
        this.contenu = contenu;
        this.dateModification = LocalDateTime.now();
    }
    
    public Boolean getAnonyme() { return anonyme; }
    public void setAnonyme(Boolean anonyme) { this.anonyme = anonyme; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
    
    public User getAuteur() { return auteur; }
    public void setAuteur(User auteur) { this.auteur = auteur; }
    
    public String getNomAnonyme() { return nomAnonyme; }
    public void setNomAnonyme(String nomAnonyme) { this.nomAnonyme = nomAnonyme; }
    
    public Integer getLikesCount() { return likesCount; }
    public void setLikesCount(Integer likesCount) { this.likesCount = likesCount; }
    
    public Integer getRepliesCount() { return repliesCount; }
    public void setRepliesCount(Integer repliesCount) { this.repliesCount = repliesCount; }
    
    public CommunityMessage getParentMessage() { return parentMessage; }
    public void setParentMessage(CommunityMessage parentMessage) { this.parentMessage = parentMessage; }
    
    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
}