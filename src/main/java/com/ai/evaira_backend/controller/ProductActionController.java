package com.ai.evaira_backend.controller;


import com.ai.evaira_backend.dto.ProductActionType;
import com.ai.evaira_backend.dto.UserProductActionRequest;
import com.ai.evaira_backend.dto.UserProductActionResponse;
import com.ai.evaira_backend.entity.User;

import com.ai.evaira_backend.service.UserProductActionService;
import com.ai.evaira_backend.service.ProductService;
import com.ai.evaira_backend.service.UserProfileService;
import com.ai.evaira_backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
public class ProductActionController {

    private final UserProductActionService actionService;
    private final JwtUtil jwtUtil;
    private final UserProfileService userProfileService;
    private final UserProductActionService productService;

    @Autowired
    public ProductActionController(UserProductActionService actionService,
                                   JwtUtil jwtUtil,
                                   UserProfileService userProfileService,
                                   UserProductActionService productService) {
        this.actionService = actionService;
        this.jwtUtil = jwtUtil;
        this.userProfileService = userProfileService;
        this.productService = productService;
    }

    private Long getUserFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);
        return userProfileService.findUserByEmail(email).getId();
    }

    /**
     * Perform action: LIKE, SAVE, OPEN, SHARE
     * Returns deeplinkUrl for OPEN/SHARE
     */
    @PostMapping
    public ResponseEntity<UserProductActionResponse> performAction(@RequestHeader("Authorization") String authHeader,
            @RequestBody UserProductActionRequest request
    ) {
        Long userId = getUserFromToken(authHeader);
        request.setUserId(userId);
        UserProductActionResponse response = actionService.performAction(request);
        return ResponseEntity.ok(response);
    }


    /**
     * Remove LIKE (unlike via swipe left)
     */
    @DeleteMapping("/like/{userId}/{productId}")
    public ResponseEntity<Void> removeLike(
            @PathVariable Long userId,
            @PathVariable Long productId
    ) {
        actionService.removeLike(userId, productId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remove SAVE (unsave)
     */
    @DeleteMapping("/save/{userId}/{productId}")
    public ResponseEntity<Void> removeSave(
            @PathVariable Long userId,
            @PathVariable Long productId
    ) {
        actionService.removeSave(userId, productId);
        return ResponseEntity.noContent().build();
    }


    /**
     * Get all liked/saved products for user
     * GET /api/actions/user/123?type=LIKE → Liked products
     * GET /api/actions/user/123?type=SAVE → Saved products
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserProductActionResponse>> getActionsForUser(
            @PathVariable Long userId,
            @RequestParam ProductActionType actionType
    ) {
        List<UserProductActionResponse> responses =
                actionService.getActionsForUser(userId, actionType);
        return ResponseEntity.ok(responses);
    }

    /**
     * Get ALL actions for user (likes + saves + opens + shares)
     * For user profile/activity tab
     */
//    @GetMapping("/user/{userId}/all")
//    public ResponseEntity<List<UserProductActionResponse>> getAllUserActions(
//            @PathVariable Long userId
//    ) {
//        List<UserProductActionResponse> responses =
//                actionService.getAllUserActions(userId);
//        return ResponseEntity.ok(responses);
//    }

    /**
     * Check if specific user liked/saved specific product
     * Used for product card icons (isLiked, isSaved)
     */
//    @GetMapping("/user/{userId}/product/{productId}")
//    public ResponseEntity<UserProductActionResponse> getUserProductAction(
//            @PathVariable Long userId,
//            @PathVariable Long productId,
//            @RequestParam(required = false) ProductActionType actionType
//    ) {
//        UserProductActionResponse response =
//                actionService.getUserProductAction(userId, productId, actionType);
//        return response != null
//                ? ResponseEntity.ok(response)
//                : ResponseEntity.notFound().build();
//    }
}

