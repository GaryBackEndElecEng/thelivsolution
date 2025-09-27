package com.navinSecurity.thirdSecJWT.repo;

import com.navinSecurity.thirdSecJWT.model.TaxModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxModelRepo extends JpaRepository<TaxModel,Long> {
    List<TaxModel> findByCountry(String country);

}
