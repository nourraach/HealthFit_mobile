package com.project.api.dto;


import java.time.LocalDateTime;

public class ErroroResponse {
    
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    
    // Constructeur complet
    public ErroroResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
    
    // Constructeur simplifié
    public ErroroResponse(String message) {
        this.timestamp = LocalDateTime.now();
        this.status = 500;
        this.error = "Internal Server Error";
        this.message = message;
        this.path = "";
    }
    


	// Getters (obligatoires pour la sérialisation JSON)
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public int getStatus() {
        return status;
    }
    
    public String getError() {
        return error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getPath() {
        return path;
    }
}
