package com.ai.evaira_backend.controller;

import com.ai.evaira_backend.dto.ProductDto;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto) {
        Product product = productService.addProduct(productDto);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}

