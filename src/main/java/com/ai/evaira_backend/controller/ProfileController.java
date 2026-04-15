package com.ai.evaira_backend.controller;


import com.ai.evaira_backend.dto.ProfileDto;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.repository.UserRepository;
import com.ai.evaira_backend.security.SecurityUtil;

import com.ai.evaira_backend.service.UserProfileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileService profileService;


    @PostMapping("/create")
    public ResponseEntity<String> createProfile(@RequestBody ProfileDto dto) {

        // ✅ NEW: 3 lines replace 8 old lines!
        Long userId = SecurityUtil.getCurrentUserId();        // From JWT token
        User user = userRepository.findById(userId)           // Fast ID lookup
                .orElseThrow(() -> new RuntimeException("User not found"));

        profileService.saveProfile(user, dto);
        return ResponseEntity.ok("Profile created/updated");
    }

    @PostMapping("tokenValidation")
    public ResponseEntity<String> validateToken(){
        return ResponseEntity.ok("ValidationSuccessfully");
    }


    @GetMapping("/me")
    public ResponseEntity<User> getMyProfile() {
    Long userId = SecurityUtil.getCurrentUserId();
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return ResponseEntity.ok(user);
}


}
