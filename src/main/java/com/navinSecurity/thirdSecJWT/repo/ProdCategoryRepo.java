package com.navinSecurity.thirdSecJWT.repo;

import com.navinSecurity.thirdSecJWT.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdCategoryRepo extends JpaRepository<ProductCategory,Long> {
    Optional<ProductCategory> findByName(String name);
}
