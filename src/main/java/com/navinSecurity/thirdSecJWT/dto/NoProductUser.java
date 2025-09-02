package com.navinSecurity.thirdSecJWT.dto;

import com.navinSecurity.thirdSecJWT.model.Role;
import com.navinSecurity.thirdSecJWT.model.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NoProductUser {
    private Long user_id;
    private String first;
    private String last;
    private String email;
    private Role role;

    public NoProductUser convert(User user){
        return NoProductUser.builder()
                .user_id(user.getUser_id())
                .first(user.getFirst())
                .last(user.getLast())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
