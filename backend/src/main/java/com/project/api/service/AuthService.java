package com.project.api.service;

import com.project.api.dto.AuthenticationRequest;
import com.project.api.dto.AuthenticationResponse;
import com.project.api.dto.InscriptionRequest;
import com.project.api.entity.User;
import com.project.api.repository.UserRepository;
import com.project.api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    
    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, 
                      JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }
    
    public AuthenticationResponse inscription(InscriptionRequest request) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByAdresseEmail(request.getAdresseEmail())) {
            throw new RuntimeException("Cette adresse email est déjà utilisée");
        }
        
        // Vérifier si le numéro de téléphone existe déjà
        if (userRepository.existsByNumTel(request.getNumTel())) {
            throw new RuntimeException("Ce numéro de téléphone est déjà utilisé");
        }
        
        // Créer le nouvel utilisateur
        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setNumTel(request.getNumTel());
        user.setAdresseEmail(request.getAdresseEmail());
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        user.setDateNaissance(request.getDateNaissance());
        user.setTaille(request.getTaille());
        user.setPoids(request.getPoids());
        user.setSexe(request.getSexe());
        user.setObjectif(request.getObjectif());
        user.setNiveauActivite(request.getNiveauActivite());
        
        User savedUser = userRepository.save(user);
        
        // Générer le token JWT avec userId
        String token = jwtUtil.generateToken(savedUser.getAdresseEmail(), savedUser.getId());
        
        return new AuthenticationResponse(
                token,
                savedUser.getId(),
                savedUser.getNom(),
                savedUser.getPrenom(),
                savedUser.getAdresseEmail()
        );
    }
    
    public AuthenticationResponse authentification(AuthenticationRequest request) {
        // Authentifier l'utilisateur
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getAdresseEmail(),
                        request.getMotDePasse()
                )
        );
        
        // Récupérer l'utilisateur
        User user = userRepository.findByAdresseEmail(request.getAdresseEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        // Générer le token JWT avec userId
        String token = jwtUtil.generateToken(user.getAdresseEmail(), user.getId());
        
        return new AuthenticationResponse(
                token,
                user.getId(),
                user.getNom(),
                user.getPrenom(),
                user.getAdresseEmail()
        );
    }
}
