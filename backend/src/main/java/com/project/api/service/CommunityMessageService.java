package com.project.api.service;

import com.project.api.dto.CommunityMessageRequest;
import com.project.api.dto.CommunityMessageResponse;
import com.project.api.entity.CommunityMessage;
import com.project.api.entity.CommunityMessageLike;
import com.project.api.entity.User;
import com.project.api.repository.CommunityMessageLikeRepository;
import com.project.api.repository.CommunityMessageRepository;
import com.project.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommunityMessageService {
    
    @Autowired
    private CommunityMessageRepository messageRepository;
    
    @Autowired
    private CommunityMessageLikeRepository messageLikeRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public CommunityMessageResponse creerMessage(CommunityMessageRequest request, Long userId) {
        User auteur = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        CommunityMessage message = new CommunityMessage(request.getContenu(), auteur, request.getAnonyme());
        
        if (request.getParentMessageId() != null) {
            CommunityMessage parentMessage = messageRepository.findById(request.getParentMessageId())
                .orElseThrow(() -> new RuntimeException("Message parent non trouvé"));
            message.setParentMessage(parentMessage);
            
            parentMessage.setRepliesCount(parentMessage.getRepliesCount() + 1);
            messageRepository.save(parentMessage);
        }
        
        message = messageRepository.save(message);
        return convertToResponse(message, userId);
    }
    
    public Page<CommunityMessageResponse> getMessages(int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommunityMessage> messages = messageRepository.findMainMessages(pageable);
        return messages.map(message -> convertToResponse(message, userId));
    }
    
    public Page<CommunityMessageResponse> getMostLikedMessages(int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommunityMessage> messages = messageRepository.findMostLikedMessages(pageable);
        return messages.map(message -> convertToResponse(message, userId));
    }
    
    public Page<CommunityMessageResponse> getRecentMessages(int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size);
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        Page<CommunityMessage> messages = messageRepository.findRecentMessages(weekAgo, pageable);
        return messages.map(message -> convertToResponse(message, userId));
    }
    
    public Page<CommunityMessageResponse> searchMessages(String keyword, int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommunityMessage> messages = messageRepository.searchMessages(keyword, pageable);
        return messages.map(message -> convertToResponse(message, userId));
    }
    
    public List<CommunityMessageResponse> getReplies(Long messageId, Long userId) {
        List<CommunityMessage> replies = messageRepository.findRepliesByParentId(messageId);
        return replies.stream()
            .map(reply -> convertToResponse(reply, userId))
            .collect(Collectors.toList());
    }
    
    public CommunityMessageResponse toggleLike(Long messageId, Long userId) {
        CommunityMessage message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message non trouvé"));
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        boolean alreadyLiked = messageLikeRepository.existsByMessageIdAndUserId(messageId, userId);
        
        if (alreadyLiked) {
            messageLikeRepository.deleteByMessageIdAndUserId(messageId, userId);
            message.setLikesCount(Math.max(0, message.getLikesCount() - 1));
        } else {
            CommunityMessageLike like = new CommunityMessageLike(message, user);
            messageLikeRepository.save(like);
            message.setLikesCount(message.getLikesCount() + 1);
        }
        
        message = messageRepository.save(message);
        return convertToResponse(message, userId);
    }
    
    public CommunityMessageResponse modifierMessage(Long messageId, CommunityMessageRequest request, Long userId) {
        CommunityMessage message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message non trouvé"));
        
        if (!message.getAuteur().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à modifier ce message");
        }
        
        message.setContenu(request.getContenu());
        message = messageRepository.save(message);
        
        return convertToResponse(message, userId);
    }
    
    public void supprimerMessage(Long messageId, Long userId) {
        CommunityMessage message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("Message non trouvé"));
        
        if (!message.getAuteur().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à supprimer ce message");
        }
        
        message.setIsDeleted(true);
        messageRepository.save(message);
        
        if (message.getParentMessage() != null) {
            CommunityMessage parent = message.getParentMessage();
            parent.setRepliesCount(Math.max(0, parent.getRepliesCount() - 1));
            messageRepository.save(parent);
        }
    }
    
    public Page<CommunityMessageResponse> getMessagesByUser(Long userId, int page, int size, Long currentUserId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommunityMessage> messages = messageRepository.findByAuteurId(userId, pageable);
        return messages.map(message -> convertToResponse(message, currentUserId));
    }
    
    private CommunityMessageResponse convertToResponse(CommunityMessage message, Long currentUserId) {
        CommunityMessageResponse response = new CommunityMessageResponse();
        
        response.setId(message.getId());
        response.setContenu(message.getContenu());
        response.setAnonyme(message.getAnonyme());
        response.setDateCreation(message.getDateCreation());
        response.setDateModification(message.getDateModification());
        response.setLikesCount(message.getLikesCount());
        response.setRepliesCount(message.getRepliesCount());
        response.setIsDeleted(message.getIsDeleted());
        
        if (message.getAnonyme()) {
            response.setNomAnonyme(message.getNomAnonyme());
        } else {
            response.setNomAuteur(message.getAuteur().getNom());
            response.setPrenomAuteur(message.getAuteur().getPrenom());
        }
        
        if (currentUserId != null) {
            boolean liked = messageLikeRepository.existsByMessageIdAndUserId(message.getId(), currentUserId);
            response.setLikedByCurrentUser(liked);
            response.setIsOwner(message.getAuteur().getId().equals(currentUserId));
        }
        
        if (message.getParentMessage() != null) {
            response.setParentMessageId(message.getParentMessage().getId());
        }
        
        return response;
    }
}