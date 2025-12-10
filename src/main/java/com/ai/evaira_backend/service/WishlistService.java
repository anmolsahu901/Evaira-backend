package com.ai.evaira_backend.service;

import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.entity.WishlistItem;
import com.ai.evaira_backend.repository.ProductRepository;
import com.ai.evaira_backend.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository,
                           ProductRepository productRepository) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public String toggleWishlist(User user, Long productId) {

        if (wishlistRepository.existsByUserIdAndProductId(user.getId(), productId)) {
            wishlistRepository.deleteByUserIdAndProductId(user.getId(), productId);
            return "removed from wishlist";
        } else {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            WishlistItem item = new WishlistItem();
            item.setUser(user);
            item.setProduct(product);
            wishlistRepository.save(item);
            return "added to wishlist";
        }
    }

    public List<Product> getWishlist(User user) {
        return wishlistRepository.findByUserId(user.getId()).stream()
                .map(WishlistItem::getProduct)
                .toList();
    }
}
