package com.project.api.dto;

public class MessageDTO {
    private Long id;
    private String role;
    private String contenu;
    private String timestamp;
    
    public MessageDTO() {
    }
    
    public MessageDTO(Long id, String role, String contenu, String timestamp) {
        this.id = id;
        this.role = role;
        this.contenu = contenu;
        this.timestamp = timestamp;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getContenu() {
        return contenu;
    }
    
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
