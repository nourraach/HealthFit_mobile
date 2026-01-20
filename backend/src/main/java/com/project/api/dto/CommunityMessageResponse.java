package com.project.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CommunityMessageResponse {
    
    private Long id;
    private String contenu;
    private Boolean anonyme;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    
    private String nomAuteur;
    private String prenomAuteur;
    private String nomAnonyme;
    
    private Integer likesCount;
    private Integer repliesCount;
    private Boolean likedByCurrentUser;
    
    private Long parentMessageId;
    private List<CommunityMessageResponse> replies;
    
    private Boolean isOwner;
    private Boolean isDeleted;
    
    // Constructeurs
    public CommunityMessageResponse() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }
    
    public Boolean getAnonyme() { return anonyme; }
    public void setAnonyme(Boolean anonyme) { this.anonyme = anonyme; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
    
    public String getNomAuteur() { return nomAuteur; }
    public void setNomAuteur(String nomAuteur) { this.nomAuteur = nomAuteur; }
    
    public String getPrenomAuteur() { return prenomAuteur; }
    public void setPrenomAuteur(String prenomAuteur) { this.prenomAuteur = prenomAuteur; }
    
    public String getNomAnonyme() { return nomAnonyme; }
    public void setNomAnonyme(String nomAnonyme) { this.nomAnonyme = nomAnonyme; }
    
    public Integer getLikesCount() { return likesCount; }
    public void setLikesCount(Integer likesCount) { this.likesCount = likesCount; }
    
    public Integer getRepliesCount() { return repliesCount; }
    public void setRepliesCount(Integer repliesCount) { this.repliesCount = repliesCount; }
    
    public Boolean getLikedByCurrentUser() { return likedByCurrentUser; }
    public void setLikedByCurrentUser(Boolean likedByCurrentUser) { this.likedByCurrentUser = likedByCurrentUser; }
    
    public Long getParentMessageId() { return parentMessageId; }
    public void setParentMessageId(Long parentMessageId) { this.parentMessageId = parentMessageId; }
    
    public List<CommunityMessageResponse> getReplies() { return replies; }
    public void setReplies(List<CommunityMessageResponse> replies) { this.replies = replies; }
    
    public Boolean getIsOwner() { return isOwner; }
    public void setIsOwner(Boolean isOwner) { this.isOwner = isOwner; }
    
    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
}