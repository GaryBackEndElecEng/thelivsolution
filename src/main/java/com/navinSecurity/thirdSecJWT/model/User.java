package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.navinSecurity.thirdSecJWT.dto.UserResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="users",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long user_id;
    private String first;
    private String last;
    private String email;
    private String password;
    private Boolean updates;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cart")
    private Set<Cart> carts;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("address")
    private Address address;
    @OneToOne(mappedBy = "user")
    @JsonBackReference("affiliate-user")
    private Affiliate affiliate;


    public User convertUser(User user){
        Role newRole;
        newRole = Role.valueOf("ROLE_USER");
        return User.builder()
                .user_id(user.getUser_id())
                .first(user.getFirst())
                .last(user.getLast())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(newRole)
                .updates(user.getUpdates())
                .carts(user.getCarts())
                .address(user.getAddress())
                .build();
    }
    public User convertAdmin(User user){
        Role newRole;
        newRole = Role.valueOf("ROLE_ADMIN");
        return User.builder()
                .user_id(user.getUser_id())
                .first(user.getFirst())
                .last(user.getLast())
                .password(user.getPassword())
                .role(newRole)
                .updates(user.getUpdates())
                .carts(user.getCarts())
                .address(user.getAddress())
                .build();
    }
    public User changePassword(User user,String hash){
        return User.builder()
                .user_id(user.getUser_id())
                .first(user.getFirst())
                .last(user.getLast())
                .password(hash)
                .role(user.getRole())
                .updates(user.getUpdates())
                .carts(user.getCarts())
                .address(user.getAddress())
                .build();
    }
    public UserResponse convert(User user){
        return UserResponse.builder()
                .user_id(user.getUser_id())
                .email(user.getEmail())
                .first(user.getFirst())
                .last(user.getLast())
                .role(user.getRole())
                .updates(user.getUpdates())
                .carts(user.getCarts())
                .address(user.getAddress())
                .build();
    }



}
