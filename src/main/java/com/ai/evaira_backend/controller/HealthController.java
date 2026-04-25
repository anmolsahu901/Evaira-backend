package com.ai.evaira_backend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @PostMapping("/checkup")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("Application is healthy");
    }

}
