package com.project.api.controller;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {
    
    @GetMapping("/ping")
    public Map<String, Object> ping() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Backend is running");
        response.put("timestamp", System.currentTimeMillis());
        response.put("server", "Spring Boot");
        return response;
    }
    
    @PostMapping("/echo")
    public Map<String, Object> echo(@RequestBody(required = false) Object body) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Echo test successful");
        response.put("receivedBody", body);
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }
}