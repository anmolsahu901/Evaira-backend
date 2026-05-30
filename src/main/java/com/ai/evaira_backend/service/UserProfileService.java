package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.ProfileDto;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.repository.UserRepository;
import com.ai.evaira_backend.repository.UserProductActionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProductActionRepository userProductActionRepository;


    @Transactional
    public void saveProfile(User user, ProfileDto dto) {

        // Basic info
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getAge() != null) user.setAge(dto.getAge());
        if (dto.getGender() != null) user.setGender(dto.getGender());
        if (dto.getLocation() != null) user.setLocation(dto.getLocation());

        // Body / face info
        if (dto.getBodyType() != null) user.setBodyType(dto.getBodyType());
        if (dto.getFaceShape() != null) user.setFaceShape(dto.getFaceShape());

        // Style preferences
        if (dto.getStyleVibes() != null) user.setStyleVibes(dto.getStyleVibes());
        if (dto.getFitTypes() != null) user.setFitTypes(dto.getFitTypes());

        // Occasion preferences
        if (dto.getPreferredOccasions() != null) user.setPreferredOccasions(dto.getPreferredOccasions());
        if (dto.getAvoidOccasions() != null) user.setAvoidOccasions(dto.getAvoidOccasions());

        // Color preferences
        if (dto.getFavoriteColors() != null) user.setFavoriteColors(dto.getFavoriteColors());
        if (dto.getDislikedColors() != null) user.setDislikedColors(dto.getDislikedColors());

        // Budget
        if (dto.getPriceBucket() != null) user.setPriceBucket(dto.getPriceBucket());

        // Save user
        userRepository.save(user);
    }

    public User findUserByEmail(String email){
       return userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void deleteUserAccount(Long userId) {
        userProductActionRepository.deleteByUserId(userId);
        userRepository.deleteById(userId);
    }

}

