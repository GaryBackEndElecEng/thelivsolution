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
    @Column(columnDefinition = "TEXT",length=1000)
    private String description;
    private String image;
    private Date created;
    private Double price;
    @ManyToOne
    @JoinColumn(name="serviceCategory_id")
    @JsonBackReference("serviceCategory-serviceMod")
    private ServiceCategory serviceCategory;
    @ManyToOne
    @JoinColumn(name="affiliate_id")
    @JsonBackReference("affiliate-serviceMod")
    private Affiliate affiliate;

    @PrePersist
    protected void onCreate(){
        this.created=new Date();
    }

    public ServiceMod convert(ServiceDtoCreate servDto,ServiceCategory servCat){
        return ServiceMod.builder()
                .name(servDto.getName())
                .cat(servDto.getCat())
                .description(servDto.getDescription())
                .image(servDto.getImage())
                .price(servDto.getPrice())
                .serviceCategory(servCat)
                .build();
    }
    public ServiceMod convertServMod(ServiceMod servMod){
        return ServiceMod.builder()
                .name(servMod.getName())
                .cat(servMod.getCat())
                .description(servMod.getDescription())
                .image(servMod.getImage())
                .created(new Date())
                .price(servMod.getPrice())
                .serviceCategory(servMod.getServiceCategory())
                .build();
    }
}
