package com.project.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.api.configuration.OllamaConfig;
import com.project.api.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OllamaService {
    
    private final RestTemplate restTemplate;
    private final OllamaConfig ollamaConfig;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public OllamaService(RestTemplate restTemplate, OllamaConfig ollamaConfig, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.ollamaConfig = ollamaConfig;
        this.objectMapper = objectMapper;
    }
    
    public String generateResponse(List<Message> historique, String nouveauMessage) {
        try {
            String url = ollamaConfig.getBaseUrl() + "/api/generate";
            
            // Construire le prompt avec l'historique
            String prompt = construirePrompt(historique, nouveauMessage);
            
            // Construire les headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Construire le body pour Ollama
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", ollamaConfig.getModel());
            requestBody.put("prompt", prompt);
            requestBody.put("stream", false); // Réponse complète, pas de streaming
            
            // Options pour contrôler la génération
            Map<String, Object> options = new HashMap<>();
            options.put("temperature", ollamaConfig.getTemperature());
            options.put("num_predict", ollamaConfig.getMaxTokens());
            requestBody.put("options", options);
            
            System.out.println("=== Ollama Request ===");
            System.out.println("URL: " + url);
            System.out.println("Model: " + ollamaConfig.getModel());
            System.out.println("Prompt length: " + prompt.length());
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            // Appeler l'API Ollama
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            // Extraire la réponse
            if (response.getBody() != null && response.getBody().containsKey("response")) {
                String aiResponse = (String) response.getBody().get("response");
                System.out.println("=== Ollama Response ===");
                System.out.println("Response length: " + aiResponse.length());
                return aiResponse.trim();
            }
            
            return "Désolé, je n'ai pas pu générer une réponse.";
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'appel à Ollama: " + e.getMessage());
            e.printStackTrace();
            return "Bonjour! Je suis votre assistant virtuel santé. (Erreur Ollama: " + e.getMessage() + ")";
        }
    }
    
    private String construirePrompt(List<Message> historique, String nouveauMessage) {
        StringBuilder prompt = new StringBuilder();
        
        // Message système
        prompt.append("Tu es un assistant virtuel spécialisé en santé, nutrition et fitness. ");
        prompt.append("Tu aides les utilisateurs avec leurs programmes de santé, leurs questions sur la nutrition, ");
        prompt.append("les exercices physiques et le bien-être général. ");
        prompt.append("Réponds de manière claire, encourageante et professionnelle en français.\n\n");
        
        // Ajouter l'historique (limité aux 8 derniers messages pour éviter un prompt trop long)
        List<Message> historiqueLimite = historique.size() > 8 
            ? historique.subList(historique.size() - 8, historique.size())
            : historique;
        
        if (!historiqueLimite.isEmpty()) {
            prompt.append("Historique de la conversation:\n");
            for (Message msg : historiqueLimite) {
                if ("user".equals(msg.getRole())) {
                    prompt.append("Utilisateur: ").append(msg.getContenu()).append("\n");
                } else if ("assistant".equals(msg.getRole())) {
                    prompt.append("Assistant: ").append(msg.getContenu()).append("\n");
                }
            }
            prompt.append("\n");
        }
        
        // Ajouter le nouveau message
        prompt.append("Utilisateur: ").append(nouveauMessage).append("\n");
        prompt.append("Assistant: ");
        
        return prompt.toString();
    }
    
    public boolean isOllamaAvailable() {
        try {
            String url = ollamaConfig.getBaseUrl() + "/api/tags";
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            System.err.println("Ollama n'est pas disponible: " + e.getMessage());
            return false;
        }
    }
    
    public List<String> getAvailableModels() {
        try {
            String url = ollamaConfig.getBaseUrl() + "/api/tags";
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            
            if (response.getBody() != null && response.getBody().containsKey("models")) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> models = (List<Map<String, Object>>) response.getBody().get("models");
                List<String> modelNames = new ArrayList<>();
                for (Map<String, Object> model : models) {
                    modelNames.add((String) model.get("name"));
                }
                return modelNames;
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des modèles: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}