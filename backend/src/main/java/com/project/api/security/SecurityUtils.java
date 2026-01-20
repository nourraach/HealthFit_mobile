package com.project.api.security;

import com.project.api.entity.User;
import com.project.api.exception.UnauthorizedException;
import com.project.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    
    private static UserRepository userRepository;
    
    @Autowired
    public SecurityUtils(UserRepository userRepository) {
        SecurityUtils.userRepository = userRepository;
    }
    
    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("Utilisateur non authentifié");
        }
        return authentication.getName();
    }
    
    public static Long getCurrentUserId() {
        String email = getCurrentUserEmail();
        User user = userRepository.findByAdresseEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Utilisateur non trouvé"));
        return user.getId();
    }
    
    public static User getCurrentUser() {
        String email = getCurrentUserEmail();
        return userRepository.findByAdresseEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Utilisateur non trouvé"));
    }
}
