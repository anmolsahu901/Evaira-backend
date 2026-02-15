package com.ai.evaira_backend.controller;


import com.ai.evaira_backend.dto.ProductActionType;
import com.ai.evaira_backend.dto.UserProductActionRequest;
import com.ai.evaira_backend.dto.UserProductActionResponse;


import com.ai.evaira_backend.security.SecurityUtil;
import com.ai.evaira_backend.service.UserProductActionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
public class ProductActionController {

    private final UserProductActionService actionService;

    @Autowired
    public ProductActionController(UserProductActionService actionService) {
        this.actionService = actionService;
    }

    /**
     * Perform action: LIKE, SAVE, OPEN, SHARE
     * Returns deeplinkUrl for OPEN/SHARE
     */
    @PostMapping
    public ResponseEntity<UserProductActionResponse> performAction(
            @RequestBody UserProductActionRequest request
    ) {
        Long userId = SecurityUtil.getCurrentUserId();
        request.setUserId(userId);

        actionService.performAction(request);
        return ResponseEntity.ok().build();
    }


    /**
     * Remove LIKE (unlike via swipe left)
     * DELETE /api/actions/like/{productId}
     */
    @DeleteMapping("/like/{productId}")
    public ResponseEntity<Void> removeLike(@PathVariable Long productId) {
        Long userId = SecurityUtil.getCurrentUserId();
        actionService.removeLike(userId, productId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remove SAVE (unsave)
     * DELETE /api/actions/save/{productId}
     */
    @DeleteMapping("/save/{productId}")
    public ResponseEntity<Void> removeSave(@PathVariable Long productId) {
        Long userId = SecurityUtil.getCurrentUserId();
        actionService.removeSave(userId, productId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get liked/saved/open/shared actions for CURRENT user
     * GET /api/actions/me?type=LIKE
     * GET /api/actions/me?type=SAVE
     */
    @GetMapping("/me")
    public ResponseEntity<List<UserProductActionResponse>> getActionsForMe(
            @RequestParam ProductActionType actionType
    ) {
        Long userId = SecurityUtil.getCurrentUserId();
        List<UserProductActionResponse> responses = actionService.getActionsForUser(userId, actionType);
        return ResponseEntity.ok(responses);
    }

    /**
     * Check if CURRENT user did an action on a product (for icons)
     * GET /api/actions/me/product/{productId}?type=LIKE
     * GET /api/actions/me/product/{productId}?type=SAVE
     */
    @GetMapping("/me/product/{productId}")
    public ResponseEntity<UserProductActionResponse> getMyProductAction(
            @PathVariable Long productId,
            @RequestParam ProductActionType actionType
    ) {
        Long userId = SecurityUtil.getCurrentUserId();
        UserProductActionResponse response = actionService.getUserProductAction(userId, productId, actionType);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    /**
     * Get ALL actions for user (likes + saves + opens + shares)
     * For user profile/activity tab
     */
    @GetMapping("/me/all")
    public ResponseEntity<List<UserProductActionResponse>> getAllMyActions() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<UserProductActionResponse> responses = actionService.getAllUserActions(userId);
        return ResponseEntity.ok(responses);
    }

}

