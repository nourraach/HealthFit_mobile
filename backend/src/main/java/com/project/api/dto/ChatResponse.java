package com.project.api.dto;

public class ChatResponse {
    private Long conversationId;
    private String userMessage;
    private String assistantMessage;
    private String timestamp;
    
    public ChatResponse() {
    }
    
    public ChatResponse(Long conversationId, String userMessage, String assistantMessage, String timestamp) {
        this.conversationId = conversationId;
        this.userMessage = userMessage;
        this.assistantMessage = assistantMessage;
        this.timestamp = timestamp;
    }
    
    public Long getConversationId() {
        return conversationId;
    }
    
    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }
    
    public String getUserMessage() {
        return userMessage;
    }
    
    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
    
    public String getAssistantMessage() {
        return assistantMessage;
    }
    
    public void setAssistantMessage(String assistantMessage) {
        this.assistantMessage = assistantMessage;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
