package com.navinSecurity.thirdSecJWT.repo;

import com.navinSecurity.thirdSecJWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

  Optional<User>  findByEmail(String email);
   Optional<User> findByFirst(String first);
   Optional<User> findByLast(String first);
//    Optional<User> findByUser_id(long user_id);
}
