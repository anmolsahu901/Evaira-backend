package com.ai.evaira_backend.controller;

import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.repository.UserRepository;
import com.ai.evaira_backend.service.NotificationService;
import com.ai.evaira_backend.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/test")
    public String test(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            return "No valid JWT principal found!";
        }
        return jwt.getSubject();
    }

    @PostMapping("/api/test-push")
    public ResponseEntity<String> sendTestPush() {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPushToken() == null || user.getPushToken().isEmpty()) {
            return ResponseEntity.badRequest().body("No push token registered for the user.");
        }

        notificationService.sendPushNotification(
                user.getPushToken(),
                "Manual curation update test! ✨",
                "This is a manually triggered test notification.",
                "discover"
        );

        return ResponseEntity.ok("Push notification sent successfully!");
    }
}
