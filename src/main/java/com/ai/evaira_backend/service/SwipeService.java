package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.SwipeDto;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.Swipe;
import com.ai.evaira_backend.entity.SwipeDirection;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.repository.ProductRepository;
import com.ai.evaira_backend.repository.SwipeRepository;
import com.ai.evaira_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class SwipeService {

    @Autowired
    private SwipeRepository swipeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public void recordSwipe(SwipeDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Swipe swipe = new Swipe();
        swipe.setUser(user);
        swipe.setProduct(product);
        swipe.setDirection(SwipeDirection.valueOf(dto.getDirection().toUpperCase()));
        swipe.setTimestamp(LocalDateTime.now());
        swipeRepository.save(swipe);
    }
}

