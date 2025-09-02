package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.LoginDto;
import com.navinSecurity.thirdSecJWT.dto.PublicUser;
import com.navinSecurity.thirdSecJWT.dto.UserResponse;
import com.navinSecurity.thirdSecJWT.model.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();

    User getUserByFirst(String first);

    User getUserByLast(String last);

    User getUserById(Long user_id);

    User registerUser(User user);

    User registerAdmin(User user);

    UserResponse logIn(LoginDto loginDto);

    PublicUser changePassword(User user, String password);
}
