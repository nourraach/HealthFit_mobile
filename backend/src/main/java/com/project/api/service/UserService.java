package com.project.api.service;

import com.project.api.dto.ChangePasswordRequest;
import com.project.api.dto.UpdateProfileRequest;
import com.project.api.dto.UserProfileResponse;
import com.project.api.entity.User;
import com.project.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UserProfileResponse getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        return mapToProfileResponse(user);
    }
    
    public UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        // Vérifier si l'email est déjà utilisé par un autre utilisateur
        if (!user.getAdresseEmail().equals(request.getAdresseEmail()) && 
            userRepository.existsByAdresseEmail(request.getAdresseEmail())) {
            throw new RuntimeException("Cette adresse email est déjà utilisée");
        }
        
        // Vérifier si le numéro de téléphone est déjà utilisé par un autre utilisateur
        if (!user.getNumTel().equals(request.getNumTel()) && 
            userRepository.existsByNumTel(request.getNumTel())) {
            throw new RuntimeException("Ce numéro de téléphone est déjà utilisé");
        }
        
        // Mettre à jour les informations
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setNumTel(request.getNumTel());
        user.setAdresseEmail(request.getAdresseEmail());
        user.setDateNaissance(request.getDateNaissance());
        user.setTaille(request.getTaille());
        user.setPoids(request.getPoids());
        user.setSexe(request.getSexe());
        user.setObjectif(request.getObjectif());
        user.setNiveauActivite(request.getNiveauActivite());
        
        User updatedUser = userRepository.save(user);
        return mapToProfileResponse(updatedUser);
    }
    
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(request.getAncienMotDePasse(), user.getMotDePasse())) {
            throw new RuntimeException("L'ancien mot de passe est incorrect");
        }
        
        // Mettre à jour le mot de passe
        user.setMotDePasse(passwordEncoder.encode(request.getNouveauMotDePasse()));
        userRepository.save(user);
    }
    
    public void deleteAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        userRepository.delete(user);
    }
    
    private UserProfileResponse mapToProfileResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getNom(),
                user.getPrenom(),
                user.getNumTel(),
                user.getAdresseEmail(),
                user.getDateNaissance() != null ? user.getDateNaissance().toString() : null,
                user.getTaille(),
                user.getPoids(),
                user.getSexe(),
                user.getObjectif(),
                user.getNiveauActivite(),
                user.calculerIMC(),
                user.calculerAge()
        );
    }
}
