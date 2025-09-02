package com.navinSecurity.thirdSecJWT.repo;

import com.navinSecurity.thirdSecJWT.model.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    @Transactional
    @Modifying
//    long deleteByCartItemId(Long cartItemId);
    @Query("DELETE FROM CartItem c WHERE c.id = :id")
    void deleteCartItemById(@Param("id") Long id);
}
