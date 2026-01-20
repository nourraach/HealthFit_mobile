package com.project.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CommunityMessageRequest {
    
    @NotBlank(message = "Le contenu du message ne peut pas être vide")
    @Size(max = 1000, message = "Le message ne peut pas dépasser 1000 caractères")
    private String contenu;
    
    private Boolean anonyme = false;
    
    private Long parentMessageId;
    
    // Constructeurs
    public CommunityMessageRequest() {}
    
    public CommunityMessageRequest(String contenu, Boolean anonyme) {
        this.contenu = contenu;
        this.anonyme = anonyme;
    }
    
    // Getters et Setters
    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }
    
    public Boolean getAnonyme() { return anonyme; }
    public void setAnonyme(Boolean anonyme) { this.anonyme = anonyme; }
    
    public Long getParentMessageId() { return parentMessageId; }
    public void setParentMessageId(Long parentMessageId) { this.parentMessageId = parentMessageId; }
}