package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Cart cart;




    public User(
            String first,
            String last,
            String email,
            String password,
            Role role,
            List<Product> products
        ) {
        this.first=first;
        this.last= last;
        this.email=email;
        this.password=password;
        this.role=role;


    };


    public User(User user){
        this.user_id=user.getUser_id();
        this.first=user.getFirst();
        this.last= user.getLast();
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.role=user.getRole();

    }
    public User convertUser(User user){
        Role newRole;
        newRole = Role.valueOf("USER");
        return User.builder()
                .user_id(user.getUser_id())
                .first(user.getFirst())
                .last(user.getLast())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(newRole)
                .build();
    }
    public User convertAdmin(User user){
        Role newRole;
        newRole = Role.valueOf("ADMIN");
        return User.builder()
                .user_id(user.getUser_id())
                .first(user.getFirst())
                .last(user.getLast())
                .password(user.getPassword())
                .role(newRole)
                .build();
    }
    public User changePassword(User user,String hash){
        return User.builder()
                .user_id(user.getUser_id())
                .first(user.getFirst())
                .last(user.getLast())
                .password(hash)
                .role(user.getRole())
                .build();
    }



}
