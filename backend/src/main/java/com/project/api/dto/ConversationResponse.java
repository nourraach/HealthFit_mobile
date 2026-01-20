package com.project.api.dto;

import java.util.List;

public class ConversationResponse {
    private Long id;
    private String titre;
    private String dateCreation;
    private String derniereActivite;
    private List<MessageDTO> messages;
    
    public ConversationResponse() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public String getDerniereActivite() {
        return derniereActivite;
    }
    
    public void setDerniereActivite(String derniereActivite) {
        this.derniereActivite = derniereActivite;
    }
    
    public List<MessageDTO> getMessages() {
        return messages;
    }
    
    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
