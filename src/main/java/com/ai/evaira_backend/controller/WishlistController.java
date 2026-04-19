package com.ai.evaira_backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ai.evaira_backend.dto.UserProductActionRequest;
import com.ai.evaira_backend.dto.enums.ProductActionType;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.security.SecurityUtil;
import com.ai.evaira_backend.service.UserProductActionService;


@RestController
@RequestMapping("/api/user/products")
public class WishlistController {

    private static final Logger log = LoggerFactory.getLogger(WishlistController.class);

    private final UserProductActionService actionService;


    public WishlistController(UserProductActionService actionService) {
        this.actionService = actionService;
    }

    // here we are retreiving products based on user actions (LIKE, SAVE) for wishlist
    @PostMapping("/getWishlistData")
        public ResponseEntity<List<Product>> basedOnUserActions( @RequestBody UserProductActionRequest request){
        Long userId = SecurityUtil.getCurrentUserId();


        return ResponseEntity.ok(actionService.getProductsByUserActions(userId,request.getActionType()));
    }


    /**
     * Get products for user actions only SEEN or OPEN 
     * GET /api/user/products/getProductBasedOnActionType
     */
    @PostMapping("/getProductBasedOnAction")
    public ResponseEntity<List<Product>> getProductBasedOnAction(
            @RequestBody UserProductActionRequest request
    ) {
        Long userId = SecurityUtil.getCurrentUserId();
        List<Product> products = actionService.getProductsByUserActions(userId, request.getActionType());

        return ResponseEntity.ok(products);
    }
}
