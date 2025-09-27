package com.navinSecurity.thirdSecJWT.repo;

import com.navinSecurity.thirdSecJWT.model.Affiliate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffilRepo extends JpaRepository<Affiliate,Long> {
    Affiliate findByCompany(String co);
}
