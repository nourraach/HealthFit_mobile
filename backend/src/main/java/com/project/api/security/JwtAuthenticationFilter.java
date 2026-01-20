package com.project.api.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    
    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            final String authHeader = request.getHeader("Authorization");
            
            System.out.println("=== JWT Filter Debug ===");
            System.out.println("Request URI: " + request.getRequestURI());
            System.out.println("Authorization Header: " + authHeader);
            
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("No valid Authorization header found");
                filterChain.doFilter(request, response);
                return;
            }
            
            final String jwt = authHeader.substring(7);
            System.out.println("JWT Token: " + jwt.substring(0, Math.min(20, jwt.length())) + "...");
            
            final String userEmail = jwtUtil.extractEmail(jwt);
            System.out.println("Extracted Email: " + userEmail);
            
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                System.out.println("User found: " + userDetails.getUsername());
                System.out.println("User authorities: " + userDetails.getAuthorities());
                
                if (jwtUtil.isTokenValid(jwt, userEmail)) {
                    System.out.println("Token is valid!");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("Authentication set in SecurityContext");
                    System.out.println("Authentication object: " + SecurityContextHolder.getContext().getAuthentication());
                    System.out.println("Is authenticated: " + SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
                    System.out.println("Principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                } else {
                    System.out.println("Token is NOT valid!");
                }
            } else if (userEmail != null) {
                System.out.println("Authentication already exists in SecurityContext");
            }
            
            System.out.println("========================");
        } catch (Exception e) {
            System.err.println("Error in JWT Filter: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("BEFORE filterChain.doFilter - Auth in context: " + 
            (SecurityContextHolder.getContext().getAuthentication() != null));
        
        filterChain.doFilter(request, response);
        
        System.out.println("AFTER filterChain.doFilter - Auth in context: " + 
            (SecurityContextHolder.getContext().getAuthentication() != null));
    }
}
