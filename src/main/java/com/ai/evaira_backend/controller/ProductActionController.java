package com.ai.evaira_backend.controller;


import com.ai.evaira_backend.dto.ProductActionType;
import com.ai.evaira_backend.entity.User;

import com.ai.evaira_backend.service.ProductActionService;
import com.ai.evaira_backend.service.ProductService;
import com.ai.evaira_backend.service.UserProfileService;
import com.ai.evaira_backend.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/actions")
public class ProductActionController {

    private final ProductActionService actionService;
    private final JwtUtil jwtUtil;
    private final UserProfileService userProfileService;
    private final ProductService productService;

    @Autowired
    public ProductActionController(ProductActionService actionService,
                                   JwtUtil jwtUtil,
                                   UserProfileService userProfileService,
                                   ProductService productService) {
        this.actionService = actionService;
        this.jwtUtil = jwtUtil;
        this.userProfileService = userProfileService;
        this.productService = productService;
    }

    private User getUserFromToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);
        return userProfileService.findUserByEmail(email);
    }

    @PostMapping("/dislike/{productId}")
    public ResponseEntity<Void> swipeLeft(@RequestHeader("Authorization") String authHeader,
                                          @PathVariable Long productId) {
        User user = getUserFromToken(authHeader);
        actionService.recordAction(user, productId, ProductActionType.DISLIKE);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/view/{productId}")
    public ResponseEntity<String> swipeRight(@RequestHeader("Authorization") String authHeader,
                                           @PathVariable Long productId) {
        User user = getUserFromToken(authHeader);
        actionService.recordAction(user, productId, ProductActionType.VIEW);
        String deeplink = productService.getProductDeepLinkById(productId);
        return ResponseEntity.ok(deeplink); // frontend then opens deeplink
    }

    @PostMapping("/like/{productId}")
    public ResponseEntity<Void> like(@RequestHeader("Authorization") String authHeader,
                                     @PathVariable Long productId) {
        User user = getUserFromToken(authHeader);
        actionService.recordAction(user, productId, ProductActionType.LIKE);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/share/{productId}")
    public ResponseEntity<String> share(@RequestHeader("Authorization") String authHeader,
                                      @PathVariable Long productId) {
        User user = getUserFromToken(authHeader);
        actionService.recordAction(user, productId, ProductActionType.SHARE);
        String deeplink = productService.getProductDeepLinkById(productId);
        return ResponseEntity.ok(deeplink);
    }
}

