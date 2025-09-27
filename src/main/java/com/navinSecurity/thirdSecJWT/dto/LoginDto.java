package com.navinSecurity.thirdSecJWT.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String password;
    private Boolean updates;
}
