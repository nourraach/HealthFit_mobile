package com.project.api.controller;

import com.project.api.dto.MessageResponse;
import com.project.api.service.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ollama")
@CrossOrigin(origins = "*")
public class OllamaTestController {
    
    private final OllamaService ollamaService;
    
    @Autowired
    public OllamaTestController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }
    
    @GetMapping("/status")
    public ResponseEntity<?> checkStatus() {
        try {
            boolean isAvailable = ollamaService.isOllamaAvailable();
            Map<String, Object> response = new HashMap<>();
            response.put("available", isAvailable);
            response.put("message", isAvailable ? "Ollama est disponible" : "Ollama n'est pas disponible");
            
            if (isAvailable) {
                List<String> models = ollamaService.getAvailableModels();
                response.put("models", models);
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erreur: " + e.getMessage()));
        }
    }
    
    @PostMapping("/test")
    public ResponseEntity<?> testGeneration(@RequestBody Map<String, String> request) {
        try {
            String prompt = request.get("prompt");
            if (prompt == null || prompt.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Le prompt est requis"));
            }
            
            // Test simple avec un prompt direct
            String response = ollamaService.generateResponse(List.of(), prompt);
            
            Map<String, Object> result = new HashMap<>();
            result.put("prompt", prompt);
            result.put("response", response);
            result.put("success", true);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erreur: " + e.getMessage()));
        }
    }
}