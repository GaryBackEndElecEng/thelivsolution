package com.navinSecurity.thirdSecJWT.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name="serviceCategories")
public class ServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy="serviceCategory",cascade= CascadeType.ALL,orphanRemoval = true)
    List<ServiceMod> services;
}
