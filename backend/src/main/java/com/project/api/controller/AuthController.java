package com.project.api.controller;

import com.project.api.dto.AuthenticationRequest;
import com.project.api.dto.AuthenticationResponse;
import com.project.api.dto.InscriptionRequest;
import com.project.api.dto.MessageResponse;
import com.project.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final AuthService authService;
    
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/inscription")
    public ResponseEntity<?> inscription(@Valid @RequestBody InscriptionRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(new MessageResponse(errors));
        }
        
        try {
            AuthenticationResponse response = authService.inscription(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @PostMapping("/authentification")
    public ResponseEntity<?> authentification(@Valid @RequestBody AuthenticationRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(new MessageResponse(errors));
        }
        
        try {
            AuthenticationResponse response = authService.authentification(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse(e.getMessage()));
        }
    }
    
    // Endpoint de compatibilité pour l'application Android
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(new MessageResponse(errors));
        }
        
        try {
            AuthenticationResponse response = authService.authentification(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse(e.getMessage()));
        }
    }
    
    // Endpoint de debug pour tester les données reçues de l'Android
    @PostMapping("/debug-auth")
    public ResponseEntity<?> debugAuth(@RequestBody(required = false) Object request) {
        System.out.println("=== DEBUG AUTH REQUEST ===");
        System.out.println("Request object: " + request);
        System.out.println("Request class: " + (request != null ? request.getClass().getName() : "null"));
        System.out.println("Request toString: " + (request != null ? request.toString() : "null"));
        
        if (request instanceof AuthenticationRequest) {
            AuthenticationRequest authReq = (AuthenticationRequest) request;
            System.out.println("Email: '" + authReq.getAdresseEmail() + "'");
            System.out.println("Password: '" + authReq.getMotDePasse() + "'");
            System.out.println("Email is null: " + (authReq.getAdresseEmail() == null));
            System.out.println("Email is empty: " + (authReq.getAdresseEmail() != null && authReq.getAdresseEmail().isEmpty()));
            System.out.println("Password is null: " + (authReq.getMotDePasse() == null));
            System.out.println("Password is empty: " + (authReq.getMotDePasse() != null && authReq.getMotDePasse().isEmpty()));
        }
        
        return ResponseEntity.ok().body(new MessageResponse("Debug info logged to console"));
    }
}
