package com.navinSecurity.thirdSecJWT.repo;

import com.navinSecurity.thirdSecJWT.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<Contact,Long> {
}
