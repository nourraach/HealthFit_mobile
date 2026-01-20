package com.project.api.controller;

import com.project.api.dto.ChatRequest;
import com.project.api.dto.ChatResponse;
import com.project.api.dto.ConversationResponse;
import com.project.api.dto.MessageResponse;
import com.project.api.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")
public class ChatBotController {
    
    private final ChatBotService chatBotService;
    
    @Autowired
    public ChatBotController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }
    
    @PostMapping("/message/{userId}")
    public ResponseEntity<?> sendMessage(@PathVariable Long userId, @RequestBody ChatRequest request) {
        try {
            ChatResponse response = chatBotService.sendMessage(userId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/conversations/{userId}")
    public ResponseEntity<?> getConversations(@PathVariable Long userId) {
        try {
            List<ConversationResponse> conversations = chatBotService.getConversations(userId);
            return ResponseEntity.ok(conversations);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<?> getConversation(@PathVariable Long conversationId) {
        try {
            ConversationResponse conversation = chatBotService.getConversation(conversationId);
            return ResponseEntity.ok(conversation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @DeleteMapping("/conversation/{conversationId}")
    public ResponseEntity<?> deleteConversation(@PathVariable Long conversationId) {
        try {
            chatBotService.deleteConversation(conversationId);
            return ResponseEntity.ok(new MessageResponse("Conversation supprimée avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
