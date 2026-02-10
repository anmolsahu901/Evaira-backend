package com.ai.evaira_backend.controller;

import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.repository.UserRepository;
import com.ai.evaira_backend.utility.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

//    private final WishlistService wishlistService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public WishlistController( JwtUtil jwtUtil, UserRepository userRepository) {
     //   this.wishlistService = wishlistService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

//    @PostMapping("/{productId}")
//    public ResponseEntity<String> toggleWishlist(
//            @RequestHeader("Authorization") String authHeader,
//            @PathVariable Long productId) {
//
//        String token = authHeader.replace("Bearer ", "");
//        String email = jwtUtil.extractUsername(token);
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String message = wishlistService.toggleWishlist(user, productId);
//        return ResponseEntity.ok(message);
//    }

//    @GetMapping
//    public ResponseEntity<List<Product>> getWishlist(
//            @RequestHeader("Authorization") String authHeader) {
//
//        String token = authHeader.replace("Bearer ", "");
//        String email = jwtUtil.extractUsername(token);
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return ResponseEntity.ok(wishlistService.getWishlist(user));
//    }
}

