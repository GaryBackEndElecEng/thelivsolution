package com.navinSecurity.thirdSecJWT.dto;

import com.navinSecurity.thirdSecJWT.model.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLoginDto {
    private String email;
    private String password;
    private Role role;
}


