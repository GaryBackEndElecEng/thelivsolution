package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.LoginDto;
import com.navinSecurity.thirdSecJWT.dto.PublicUser;
import com.navinSecurity.thirdSecJWT.dto.UserResponse;
import com.navinSecurity.thirdSecJWT.model.User;

import java.util.List;

public interface IUserService {
    List<User> getAdminUsers(Long user_id);

    List<PublicUser> getSafeUsers();

    User getUserByFirst(String first);

    User getUserByLast(String last);

    User getUserById(Long user_id);

    User registerUser(User user);

    User registerAdmin(User user,Long user_id);

    UserResponse logIn(LoginDto loginDto);

    UserResponse changePassword(Long user_id, String oldPassword,String newPassword);

    Long deleteUser(Long ownerId,Long user_id);

    UserResponse adminPostNewUser(User user,Long owner_id);
}
