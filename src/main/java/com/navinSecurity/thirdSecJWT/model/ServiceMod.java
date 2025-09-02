package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.navinSecurity.thirdSecJWT.dto.ServiceDtoCreate;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="services")
public class ServiceMod {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String cat;
    private String description;
    private String image;
    private Date created;
    private Double price;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name="serviceCategory_id")
    @JsonBackReference
    private ServiceCategory serviceCategory;

    @PrePersist
    protected void onCreate(){
        this.created=new Date();
    }

    public ServiceMod convert(ServiceDtoCreate servDto){
        return ServiceMod.builder()
                .name(servDto.getName())
                .cat(servDto.getCat())
                .description(servDto.getDesc())
                .image(servDto.getImage())
                .build();
    }
}
