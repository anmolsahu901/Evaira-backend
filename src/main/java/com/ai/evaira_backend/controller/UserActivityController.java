package com.ai.evaira_backend.controller;

import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.service.ProductService;
import com.ai.evaira_backend.service.UserProductActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/products")
public class UserActivityController {

    private static final Logger log = LoggerFactory.getLogger(UserActivityController.class);

    private final ProductService productService;
    private final UserProductActionService actionService;

    public UserActivityController(ProductService productService, UserProductActionService actionService) {
        this.productService = productService;
        this.actionService = actionService;
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAll() {
        log.info("fetching products from DB ");
        return ResponseEntity.ok(productService.getAllFromDb());
    }






}
