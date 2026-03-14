package com.ai.evaira_backend.repository;

import com.ai.evaira_backend.dto.ProductActionType;
import com.ai.evaira_backend.entity.Product;
import com.ai.evaira_backend.entity.UserProductAction;
import com.ai.evaira_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserProductActionRepository extends JpaRepository<UserProductAction, Long> {
    //Get ALL actions this user has ever done on ANY products.
    List<UserProductAction> findByUserId(Long userId);

    //    When rendering a product card in feed:
//    "Is this product LIKED by current user?" → pass LIKE
//    "Is this product SAVED by current user?" → pass SAVE
    // Check if this specific user has performed this specific action on this specific product.
    Optional<UserProductAction> findByUserAndProductAndActionType(User user,
                                                                  Product product,
                                                                  ProductActionType actionType);

    //    Get all products that this user has performed one specific action on
//    Used for building dedicated screens- wishlist and liked by user screen
    List<UserProductAction> findByUserAndActionType(User user, ProductActionType actionType);

    @Query("SELECT upa FROM UserProductAction upa WHERE upa.user.id = :userId AND upa.actionType = :actionType")
    List<UserProductAction> findByUserIdAndActionType(@Param("userId") Long userId, @Param("actionType") ProductActionType actionType);

    @Query("SELECT upa FROM UserProductAction upa WHERE upa.user.id = :userId AND upa.product.externalId = :productId AND upa.actionType = :actionType")
    Optional<UserProductAction> findByUserIdAndProductIdAndActionType(@Param("userId") Long userId, @Param("productId") Long productId, @Param("actionType") ProductActionType actionType);

    @Query("SELECT upa.product FROM UserProductAction upa WHERE upa.user.id = :userId AND upa.actionType = :actionTypes")
    List<Product> findProductsByUserIdAndActionTypes(@Param("userId") Long userId, @Param("actionTypes") ProductActionType actionType);


}
