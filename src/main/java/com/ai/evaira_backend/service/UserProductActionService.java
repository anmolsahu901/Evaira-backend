package com.ai.evaira_backend.service;

import com.ai.evaira_backend.dto.enums.ProductActionType;
import com.ai.evaira_backend.dto.UserProductActionRequest;
import com.ai.evaira_backend.dto.UserProductActionResponse;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.User;
import com.ai.evaira_backend.entity.UserProductAction;
import com.ai.evaira_backend.repository.UserProductActionRepository;
import com.ai.evaira_backend.repository.ProductRepository;
import com.ai.evaira_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class UserProductActionService {

    private final UserProductActionRepository actionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final Logger log =  LoggerFactory.getLogger(UserProductActionService.class);

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
                log.info("like hit by user : {} and productId: {}",user.getEmail(),product.getExternalId());
                handleLike(user, product);
                return null;
            }
            case SAVE -> {
                log.info("save hit by user: {}",user.getEmail());
                handleSave(user, product);
                return null;
            }
            case OPEN -> {
                log.info("open hit by user: {}",user.getEmail());
                handleOpen(user, product);
                return null;
            }
            case SHARE -> {
                log.info("share hit by user: {}",user.getEmail());
                handleShare(user, product);
                return null;
            }
            case DISLIKE -> {
                log.info("dislike hit by user: {}",user.getEmail());
                handleDislike(user, product);
                return null;
            }
            case UNLIKE -> {
                removeLike(user.getId(),product.getId());
                log.info("unlike hit by user: {}",user.getEmail());
                return null;
            } case UNSAVE -> {
                removeSave(user.getId(),product.getId());
                log.info("unsave hit by user: {}",user.getEmail());
                return null;
            }

            case SEEN -> {
                log.info("seen hit by user: {}",user.getEmail());
                handleSeen(user, product);
                return null;
            }

            default -> throw new IllegalArgumentException("Unsupported action: " + type);
        }
    }

    @Transactional
    public void performBulkAction(UserProductActionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getProductIds() == null || request.getProductIds().isEmpty()) {
            return;
        }

        List<Product> products = productRepository.findAllById(request.getProductIds());
        ProductActionType type = request.getActionType();

        if (type == ProductActionType.SEEN) {
            log.info("bulk seen hit by user: {}, count: {}", user.getEmail(), products.size());
            handleBulkSeen(user, products);
        } else {
            throw new IllegalArgumentException("Unsupported bulk action: " + type);
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

    @Transactional
    protected void handleSeen(User user, Product product) {
        actionRepository.findByUserAndProductAndActionType(user, product, ProductActionType.SEEN)
                .ifPresentOrElse(
                        existing -> {
                            existing.setCreatedAt(Instant.now());
                            actionRepository.save(existing);
                        },
                        () -> {
                            UserProductAction action = new UserProductAction();
                            action.setUser(user);
                            action.setProduct(product);
                            action.setActionType(ProductActionType.SEEN);
                            actionRepository.save(action);
                        });
    }

    @Transactional
    protected void handleBulkSeen(User user, List<Product> products) {
        for (Product product : products) {
            handleSeen(user, product);
        }
    }

    public List<Product> getProductsByUserActions(Long userId,ProductActionType actionType) {
        if (actionType == ProductActionType.SEEN) {
            return actionRepository.findTop50ByUserIdAndActionTypeOrderByCreatedAtDesc(userId, actionType)
                    .stream()
                    .map(UserProductAction::getProduct)
                    .toList();
        }
        return actionRepository.findProductsByUserIdAndActionTypes(userId, actionType);
    }

    public List<UserProductActionResponse> getActionsForUser(Long userId, ProductActionType type) {
        if (type == ProductActionType.SEEN) {
            return actionRepository.findTop50ByUserIdAndActionTypeOrderByCreatedAtDesc(userId, type)
                    .stream()
                    .map(this::mapToResponseWithDeeplink)
                    .toList();
        }
        return actionRepository.findByUserIdAndActionType(userId, type)
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
