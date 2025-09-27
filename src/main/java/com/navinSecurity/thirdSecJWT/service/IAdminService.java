package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.AdminLoginDto;
import com.navinSecurity.thirdSecJWT.dto.UserResponse;
import com.navinSecurity.thirdSecJWT.model.User;

public interface IAdminService {
    UserResponse login(AdminLoginDto adminLoginDto);

}
