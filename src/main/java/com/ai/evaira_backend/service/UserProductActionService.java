package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.ProductActionType;
import com.ai.evaira_backend.dto.UserProductActionRequest;
import com.ai.evaira_backend.dto.UserProductActionResponse;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.entity.UserProductAction;
import com.ai.evaira_backend.repository.UserProductActionRepository;
import com.ai.evaira_backend.repository.ProductRepository;
import com.ai.evaira_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class UserProductActionService {

    private final UserProductActionRepository actionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public UserProductActionService(UserProductActionRepository actionRepository,
            ProductRepository productRepository, UserRepository userRepository) {
        this.actionRepository = actionRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserProductActionResponse performAction(UserProductActionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        ProductActionType type = request.getActionType();

        switch (type) {
            case LIKE -> {
                handleLike(user, product);
                return null;
            }
            case SAVE -> {
                handleSave(user, product);
                return null;
            }
            case OPEN -> {
                handleOpen(user, product);
                return null;
            }
            case SHARE -> {
                handleShare(user, product);
                return null;
            }
            case DISLIKE -> {
                handleDislike(user, product);
                return null;
            }
            default -> throw new IllegalArgumentException("Unsupported action: " + type);
        }
    }

    @Transactional
    protected void handleLike(User user, Product product) {
        actionRepository.findByUserAndProductAndActionType(user, product, ProductActionType.LIKE)
                .ifPresentOrElse(
                        existing -> {
                            // Like action already exists, no further action needed
                        },
                        () -> {
                            // create new like
                            UserProductAction action = new UserProductAction();
                            action.setUser(user);
                            action.setProduct(product);
                            action.setActionType(ProductActionType.LIKE);

                            // increment likes count
                            product.setLikesCount(product.getLikesCount() + 1);
                            productRepository.save(product);

                            actionRepository.save(action);
                        });
    }

    @Transactional
    public void removeLike(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        actionRepository.findByUserAndProductAndActionType(user, product, ProductActionType.LIKE)
                .ifPresent(action -> {
                    actionRepository.delete(action);
                    // decrement likes count
                    product.setLikesCount(Math.max(0, product.getLikesCount() - 1));
                    productRepository.save(product);
                });
    }

    @Transactional
    protected void handleSave(User user, Product product) {
        actionRepository.findByUserAndProductAndActionType(user, product, ProductActionType.SAVE)
                .ifPresentOrElse(
                        existing -> {
                            // Action already exists, no further action needed
                        },
                        () -> {
                            UserProductAction action = new UserProductAction();
                            action.setUser(user);
                            action.setProduct(product);
                            action.setActionType(ProductActionType.SAVE);
                            actionRepository.save(action);
                        });
    }

    private void handleDislike(User user, Product product) {
        actionRepository.findByUserAndProductAndActionType(user, product, ProductActionType.DISLIKE)
                .ifPresentOrElse(
                        existing -> {
                            // Action already exists, no further action needed
                        },
                        () -> {
                            UserProductAction action = new UserProductAction();
                            action.setUser(user);
                            action.setProduct(product);
                            action.setActionType(ProductActionType.DISLIKE);
                            actionRepository.save(action);
                        });
    }

    @Transactional
    public void removeSave(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        actionRepository.findByUserAndProductAndActionType(user, product, ProductActionType.SAVE)
                .ifPresent(actionRepository::delete);
    }

    @Transactional
    protected void handleOpen(User user, Product product) {
        actionRepository.findByUserAndProductAndActionType(user, product, ProductActionType.OPEN)
                .ifPresentOrElse(
                        existing -> {
                            // Update the createdAt timestamp
                            existing.setCreatedAt(Instant.now());
                            actionRepository.save(existing);
                        },
                        () -> {
                            // Create new open action
                            UserProductAction action = new UserProductAction();
                            action.setUser(user);
                            action.setProduct(product);
                            action.setActionType(ProductActionType.OPEN);
                            actionRepository.save(action);
                        });
    }

    @Transactional
    protected void handleShare(User user, Product product) {
        UserProductAction action = new UserProductAction();
        action.setUser(user);
        action.setProduct(product);
        action.setActionType(ProductActionType.SHARE);
        actionRepository.save(action);
    }

    public List<UserProductActionResponse> getActionsForUser(Long userId, ProductActionType type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return actionRepository.findByUserAndActionType(user, type)
                .stream()
                .map(this::mapToResponseWithDeeplink)
                .toList();
    }

    // ✅ NEW 1: Get ALL actions for user (likes + saves + opens + shares)
    public List<UserProductActionResponse> getAllUserActions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return actionRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponseWithDeeplink)
                .toList();
    }

    // ✅ NEW 2: Check if specific user has a specific action on a product
    // Returns null if no such action exists
    public UserProductActionResponse getUserProductAction(Long userId,
            Long productId,
            ProductActionType actionType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        return actionRepository
                .findByUserAndProductAndActionType(user, product, actionType)
                .map(this::mapToResponseWithDeeplink)
                .orElse(null);
    }

    private UserProductActionResponse mapToResponseWithDeeplink(UserProductAction action) {
        UserProductActionResponse dto = mapToResponse(action);
        dto.setDeeplinkUrl(action.getProduct().getDeeplinkUrl());
        return dto;
    }

    private UserProductActionResponse mapToResponse(UserProductAction action) {
        UserProductActionResponse dto = new UserProductActionResponse();
        dto.setId(action.getId());
        dto.setUserId(action.getUser().getId());
        dto.setProductId(action.getProduct().getId());
        dto.setActionType(action.getActionType());
        dto.setCreatedAt(action.getCreatedAt());
        dto.setDeeplinkUrl(action.getProduct().getDeeplinkUrl()); // ✅ ADDED
        return dto;
    }
}
