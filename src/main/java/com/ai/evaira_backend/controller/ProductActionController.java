package com.ai.evaira_backend.controller;


import com.ai.evaira_backend.dto.enums.ProductActionType;
import com.ai.evaira_backend.dto.UserProductActionRequest;
import com.ai.evaira_backend.dto.UserProductActionResponse;
import com.ai.evaira_backend.security.SecurityUtil;
import com.ai.evaira_backend.service.UserProductActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/actions")
public class ProductActionController {

    private static final Logger log = LoggerFactory.getLogger(ProductActionController.class);

    private final UserProductActionService actionService;

    
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

    @PostMapping("/bulk/seen")
    public ResponseEntity<Void> performBulkAction(@RequestBody UserProductActionRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        request.setUserId(userId);
        log.info("Marking product SEEN for user : {}", userId.toString());
        actionService.performBulkAction(request);
        return ResponseEntity.ok().build();
    }





    // currently not in use
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



}

