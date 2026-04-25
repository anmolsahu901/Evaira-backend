package com.ai.evaira_backend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @RequestMapping(value = "/checkup", method = RequestMethod.HEAD)
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("Application is healthy");
    }

}
