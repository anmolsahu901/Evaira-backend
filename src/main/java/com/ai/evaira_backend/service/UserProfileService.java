package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.ProfileDto;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.entity.UserPreference;
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
        // basic fields
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setGender(dto.getGender());
        user.setLocation(dto.getLocation());
        user.setBodyType(dto.getBodyType());
        user.setFaceShape(dto.getFaceShape());

        // clear old preferences (if any)
        user.getPreferences().clear();

        // occasions
        if (dto.getPreferredOccasions() != null) {
            for (String occ : dto.getPreferredOccasions()) {
                UserPreference p = new UserPreference();
                p.setUser(user);
                p.setType("OCCASION");
                p.setValue(occ);
                user.getPreferences().add(p);
            }
        }

        // colors
        if (dto.getFavoriteColors() != null) {
            for (String color : dto.getFavoriteColors()) {
                UserPreference p = new UserPreference();
                p.setUser(user);
                p.setType("COLOR");
                p.setValue(color);
                user.getPreferences().add(p);
            }
        }

        userRepository.save(user); // cascades and saves preferences
    }

    public User findUserByEmail(String email){
       return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}

