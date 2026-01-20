package com.project.api.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.api.dto.ChatRequest;
import com.project.api.dto.ChatResponse;
import com.project.api.dto.ConversationResponse;
import com.project.api.dto.MessageDTO;
import com.project.api.entity.Conversation;
import com.project.api.entity.Message;
import com.project.api.entity.User;
import com.project.api.repository.ConversationRepository;
import com.project.api.repository.MessageRepository;
import com.project.api.repository.UserRepository;
import com.project.api.service.OllamaService;

@Service
public class ChatBotService {
    
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final OllamaService ollamaService;
    
    @Autowired
    public ChatBotService(ConversationRepository conversationRepository,
                         MessageRepository messageRepository,
                         UserRepository userRepository,
                         OllamaService ollamaService) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.ollamaService = ollamaService;
    }
    
    @Transactional
    public ChatResponse sendMessage(Long userId, ChatRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        Conversation conversation;
        
        if (request.getConversationId() == null) {
            // Nouvelle conversation
            String titre = genererTitre(request.getMessage());
            conversation = new Conversation(user, titre);
            conversation = conversationRepository.save(conversation);
        } else {
            // Conversation existante
            conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation non trouvée"));
        }
        
        // Sauvegarder le message de l'utilisateur
        Message userMessage = new Message(conversation, request.getMessage(), "user");
        messageRepository.save(userMessage);
        
        // Récupérer l'historique de la conversation
        List<Message> historique = messageRepository.findByConversationIdOrderByTimestampAsc(conversation.getId());
        
        // Appeler Ollama
        String reponseIA = ollamaService.generateResponse(historique, request.getMessage());
        
        // Sauvegarder la réponse de l'assistant
        Message assistantMessage = new Message(conversation, reponseIA, "assistant");
        messageRepository.save(assistantMessage);
        
        // Mettre à jour la dernière activité
        conversation.setDerniereActivite(LocalDateTime.now());
        conversationRepository.save(conversation);
        
        return new ChatResponse(
            conversation.getId(),
            request.getMessage(),
            reponseIA,
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }
    
    private String genererTitre(String premierMessage) {
        if (premierMessage == null || premierMessage.trim().isEmpty()) {
            return "Nouvelle conversation";
        }
        
        String titre = premierMessage.trim();
        if (titre.length() > 50) {
            titre = titre.substring(0, 47) + "...";
        }
        return titre;
    }
    
    public List<ConversationResponse> getConversations(Long userId) {
        List<Conversation> conversations = conversationRepository.findByUserIdOrderByDerniereActiviteDesc(userId);
        
        return conversations.stream().map(conv -> {
            ConversationResponse response = new ConversationResponse();
            response.setId(conv.getId());
            response.setTitre(conv.getTitre());
            response.setDateCreation(conv.getDateCreation().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            response.setDerniereActivite(conv.getDerniereActivite().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            
            List<MessageDTO> messagesDTO = conv.getMessages().stream().map(msg -> 
                new MessageDTO(
                    msg.getId(),
                    msg.getRole(),
                    msg.getContenu(),
                    msg.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                )
            ).collect(Collectors.toList());
            
            response.setMessages(messagesDTO);
            return response;
        }).collect(Collectors.toList());
    }
    
    // AJOUTEZ CES DEUX MÉTHODES:
    
    public ConversationResponse getConversation(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
            .orElseThrow(() -> new RuntimeException("Conversation non trouvée avec ID: " + conversationId));
        
        ConversationResponse response = new ConversationResponse();
        response.setId(conversation.getId());
        response.setTitre(conversation.getTitre());
        response.setDateCreation(conversation.getDateCreation().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        response.setDerniereActivite(conversation.getDerniereActivite().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        // Récupérer tous les messages de cette conversation
        List<Message> messages = messageRepository.findByConversationIdOrderByTimestampAsc(conversationId);
        
        List<MessageDTO> messagesDTO = messages.stream().map(msg -> 
            new MessageDTO(
                msg.getId(),
                msg.getRole(),
                msg.getContenu(),
                msg.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            )
        ).collect(Collectors.toList());
        
        response.setMessages(messagesDTO);
        return response;
    }
    
    @Transactional
    public void deleteConversation(Long conversationId) {
        // Vérifier d'abord si la conversation existe
        if (!conversationRepository.existsById(conversationId)) {
            throw new RuntimeException("Conversation non trouvée avec ID: " + conversationId);
        }
        
        // Supprimer la conversation (les messages seront supprimés en cascade)
        conversationRepository.deleteById(conversationId);
    }
}