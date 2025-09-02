package com.navinSecurity.thirdSecJWT.repo;

import com.navinSecurity.thirdSecJWT.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByCat(String cat);
    Optional<Product> findByName(String name);
}
