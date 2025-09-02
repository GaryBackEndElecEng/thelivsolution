package com.navinSecurity.thirdSecJWT.dto;

import com.navinSecurity.thirdSecJWT.model.Role;
import com.navinSecurity.thirdSecJWT.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PublicUser {
    private Long user_id;
    private String first;
    private String last;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    public PublicUser convert(User user){
        return PublicUser.builder()
                .user_id(user.getUser_id())
                .email(user.getEmail())
                .first(user.getFirst())
                .last(user.getLast())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
