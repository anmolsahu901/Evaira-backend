package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.ProfileDto;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void saveProfile(User user, ProfileDto dto) {

        // Basic info
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setGender(dto.getGender());
        user.setLocation(dto.getLocation());

        // Body / face info
        user.setBodyType(dto.getBodyType());
        user.setFaceShape(dto.getFaceShape());

        // Style preferences
        user.setStyleVibes(dto.getStyleVibes());
        user.setFitTypes(dto.getFitTypes());

        // Occasion preferences
        user.setPreferredOccasions(dto.getPreferredOccasions());
        user.setAvoidOccasions(dto.getAvoidOccasions());

        // Color preferences
        user.setFavoriteColors(dto.getFavoriteColors());
        user.setDislikedColors(dto.getDislikedColors());

        // Budget
        user.setPriceBucket(dto.getPriceBucket());

        // Save user
        userRepository.save(user);
    }

    public User findUserByEmail(String email){
       return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}

