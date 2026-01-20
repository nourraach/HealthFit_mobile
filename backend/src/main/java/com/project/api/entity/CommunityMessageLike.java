package com.project.api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "community_message_likes", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"message_id", "user_id"}))
public class CommunityMessageLike {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private CommunityMessage message;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;
    
    // Constructeurs
    public CommunityMessageLike() {
        this.dateCreation = LocalDateTime.now();
    }
    
    public CommunityMessageLike(CommunityMessage message, User user) {
        this();
        this.message = message;
        this.user = user;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public CommunityMessage getMessage() { return message; }
    public void setMessage(CommunityMessage message) { this.message = message; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
}