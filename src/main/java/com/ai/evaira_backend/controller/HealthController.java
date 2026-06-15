package com.ai.evaira_backend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    public HealthController(org.springframework.jdbc.core.JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @RequestMapping(value = "/checkup", method = {RequestMethod.GET, RequestMethod.HEAD})
    public ResponseEntity<String> healthCheck(){
        try {
            // Perform a lightweight query to ensure the DB connection is healthy
            jdbcTemplate.execute("SELECT 1");
            return ResponseEntity.ok("Application and Database are healthy");
        } catch (Exception e) {
            return ResponseEntity.status(503).body("Database connection is down");
        }
    }

}

