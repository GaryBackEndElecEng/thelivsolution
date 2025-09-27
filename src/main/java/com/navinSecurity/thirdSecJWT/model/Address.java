package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String street;
    private String city;
    private String prov_state;
    private String country;
    @OneToOne
    @JoinColumn(name = "user_id") // Foreign key column in Cart table
    @JsonBackReference("address")
    private User user;
}
