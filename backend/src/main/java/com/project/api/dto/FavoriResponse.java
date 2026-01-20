package com.project.api.dto;

public class FavoriResponse {
    
    private boolean success;
    private String message;
    private boolean isFavorite;
    private Long totalFavorites;
    
    // Constructeurs
    public FavoriResponse() {}
    
    public FavoriResponse(boolean success, String message, boolean isFavorite) {
        this.success = success;
        this.message = message;
        this.isFavorite = isFavorite;
    }
    
    public FavoriResponse(boolean success, String message, boolean isFavorite, Long totalFavorites) {
        this.success = success;
        this.message = message;
        this.isFavorite = isFavorite;
        this.totalFavorites = totalFavorites;
    }
    
    // Getters et Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
    
    public Long getTotalFavorites() { return totalFavorites; }
    public void setTotalFavorites(Long totalFavorites) { this.totalFavorites = totalFavorites; }
}