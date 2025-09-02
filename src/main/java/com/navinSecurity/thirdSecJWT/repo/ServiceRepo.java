package com.navinSecurity.thirdSecJWT.repo;

import com.navinSecurity.thirdSecJWT.model.ServiceMod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepo  extends JpaRepository<ServiceMod,Long> {
    List<ServiceMod> findByCat(String cat);
    Optional<ServiceMod> findByName(String name);
}
