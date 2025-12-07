package com.ai.evaira_backend.controller;

import com.ai.evaira_backend.dto.SwipeDto;
import com.ai.evaira_backend.service.SwipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/swipes")
public class SwipeController {

    @Autowired
    private SwipeService swipeService;

    @PostMapping
    public ResponseEntity<String> recordSwipe(@RequestBody SwipeDto swipeDto, @RequestParam Long userId) {
        swipeService.recordSwipe(swipeDto, userId);
        return ResponseEntity.ok("Swipe recorded.");
    }
}

