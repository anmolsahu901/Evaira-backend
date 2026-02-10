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
                return handleLike(user, product);
            }
            case SAVE -> {
                return handleSave(user, product);
            }
            case OPEN -> {
                return handleOpen(user, product);
            }
            case SHARE -> {
                return handleShare(user, product);
            }
            default -> throw new IllegalArgumentException("Unsupported action: " + type);
        }
    }

    @Transactional
    protected UserProductActionResponse handleLike(User user, Product product) {
        return actionRepository.findByUserAndProductAndActionType(user, product, ProductActionType.LIKE)
                .map(existing -> mapToResponse(existing))
                .orElseGet(() -> {
                    // create new like
                    UserProductAction action = new UserProductAction();
                    action.setUser(user);
                    action.setProduct(product);
                    action.setActionType(ProductActionType.LIKE);

                    // increment likes count
                    product.setLikesCount(product.getLikesCount() + 1);
                    productRepository.save(product);

                    UserProductAction saved = actionRepository.save(action);
                    return mapToResponse(saved);
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
    protected UserProductActionResponse handleSave(User user, Product product) {
        return actionRepository.findByUserAndProductAndActionType(user, product, ProductActionType.SAVE)
                .map(existing -> mapToResponse(existing))
                .orElseGet(() -> {
                    UserProductAction action = new UserProductAction();
                    action.setUser(user);
                    action.setProduct(product);
                    action.setActionType(ProductActionType.SAVE);
                    UserProductAction saved = actionRepository.save(action);
                    return mapToResponse(saved);
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
    protected UserProductActionResponse handleOpen(User user, Product product) {
        UserProductAction action = new UserProductAction();
        action.setUser(user);
        action.setProduct(product);
        action.setActionType(ProductActionType.OPEN);
        UserProductAction saved = actionRepository.save(action);
        return mapToResponseWithDeeplink(saved);
    }

    @Transactional
    protected UserProductActionResponse handleShare(User user, Product product) {
        UserProductAction action = new UserProductAction();
        action.setUser(user);
        action.setProduct(product);
        action.setActionType(ProductActionType.SHARE);
        UserProductAction saved = actionRepository.save(action);
        return mapToResponseWithDeeplink(saved);
    }

    public List<UserProductActionResponse> getActionsForUser(Long userId, ProductActionType type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return actionRepository.findByUserAndActionType(user, type)
                .stream()
                .map(this::mapToResponseWithDeeplink)
                .toList();
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
        dto.setDeeplinkUrl(action.getProduct().getDeeplinkUrl());  // ✅ ADDED
        return dto;
    }
}
