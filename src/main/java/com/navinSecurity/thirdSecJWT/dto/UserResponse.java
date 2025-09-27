package com.navinSecurity.thirdSecJWT.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.navinSecurity.thirdSecJWT.model.Address;
import com.navinSecurity.thirdSecJWT.model.Cart;
import com.navinSecurity.thirdSecJWT.model.Role;
import com.navinSecurity.thirdSecJWT.model.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class UserResponse {
    private Long user_id;
    private String first;
    private String last;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String jwt;
    @JsonManagedReference("cart")
    private Set<Cart> carts;
    @JsonManagedReference("address")
    private Address address;
    private Boolean updates;

    public UserResponse convert(User user,String jwt){
        return UserResponse.builder()
                .user_id(user.getUser_id())
                .email(user.getEmail())
                .first(user.getFirst())
                .last(user.getLast())
                .email(user.getEmail())
                .role(user.getRole())
                .jwt(jwt)
                .carts(user.getCarts())
                .address(user.getAddress())
                .updates(user.getUpdates())
                .build();
    }

}
