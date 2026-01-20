package com.project.api.controller;

import com.project.api.dto.CommunityMessageRequest;
import com.project.api.dto.CommunityMessageResponse;
import com.project.api.service.CommunityMessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class CommunityMessageController {
    
    @Autowired
    private CommunityMessageService messageService;
    
    @PostMapping
    public ResponseEntity<CommunityMessageResponse> creerMessage(@Valid @RequestBody CommunityMessageRequest request) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            CommunityMessageResponse message = messageService.creerMessage(request, userId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    public ResponseEntity<Page<CommunityMessageResponse>> getMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "recent") String sort) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            Page<CommunityMessageResponse> messages;
            
            switch (sort) {
                case "liked":
                    messages = messageService.getMostLikedMessages(page, size, userId);
                    break;
                case "recent":
                default:
                    messages = messageService.getMessages(page, size, userId);
                    break;
            }
            
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<CommunityMessageResponse>> searchMessages(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            Page<CommunityMessageResponse> messages = messageService.searchMessages(keyword, page, size, userId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/{messageId}/replies")
    public ResponseEntity<List<CommunityMessageResponse>> getReplies(@PathVariable Long messageId) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            List<CommunityMessageResponse> replies = messageService.getReplies(messageId, userId);
            return ResponseEntity.ok(replies);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{messageId}/like")
    public ResponseEntity<CommunityMessageResponse> toggleLike(@PathVariable Long messageId) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            CommunityMessageResponse message = messageService.toggleLike(messageId, userId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{messageId}")
    public ResponseEntity<CommunityMessageResponse> modifierMessage(
            @PathVariable Long messageId,
            @Valid @RequestBody CommunityMessageRequest request) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            CommunityMessageResponse message = messageService.modifierMessage(messageId, request, userId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> supprimerMessage(@PathVariable Long messageId) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            messageService.supprimerMessage(messageId, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<CommunityMessageResponse>> getMessagesByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Long currentUserId = com.project.api.security.SecurityUtils.getCurrentUserId();
            Page<CommunityMessageResponse> messages = messageService.getMessagesByUser(userId, page, size, currentUserId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/my-messages")
    public ResponseEntity<Page<CommunityMessageResponse>> getMyMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Long userId = com.project.api.security.SecurityUtils.getCurrentUserId();
            Page<CommunityMessageResponse> messages = messageService.getMessagesByUser(userId, page, size, userId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}