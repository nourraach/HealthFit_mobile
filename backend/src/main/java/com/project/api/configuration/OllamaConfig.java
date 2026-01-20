package com.project.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {
    
    @Value("${ai.base.url}")
    private String baseUrl;
    
    @Value("${ai.model}")
    private String model;
    
    @Value("${ai.max.tokens}")
    private Integer maxTokens;
    
    @Value("${ai.temperature}")
    private Double temperature;
    
    // Getters
    public String getBaseUrl() {
        return baseUrl;
    }
    
    public String getModel() {
        return model;
    }
    
    public Integer getMaxTokens() {
        return maxTokens;
    }
    
    public Double getTemperature() {
        return temperature;
    }
}