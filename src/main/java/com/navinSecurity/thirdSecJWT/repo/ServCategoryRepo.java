package com.navinSecurity.thirdSecJWT.repo;

import com.navinSecurity.thirdSecJWT.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServCategoryRepo extends JpaRepository<ServiceCategory,Long> {
    Optional<ServiceCategory> findByName(String name);
}
