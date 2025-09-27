package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="serviceCategories")
public class ServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy="serviceCategory",cascade= CascadeType.ALL,orphanRemoval = false)
    @JsonManagedReference("serviceCategory-serviceMod")
    List<ServiceMod> services;


    public ServiceCategory(String name){
        this.name=name;
        this.services=new ArrayList<ServiceMod>();

    }
}
