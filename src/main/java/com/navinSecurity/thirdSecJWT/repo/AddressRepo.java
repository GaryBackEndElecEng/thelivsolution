package com.navinSecurity.thirdSecJWT.repo;

import com.navinSecurity.thirdSecJWT.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address,Long> {

}
