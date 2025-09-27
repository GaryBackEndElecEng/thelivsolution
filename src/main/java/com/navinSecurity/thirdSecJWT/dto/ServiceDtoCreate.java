package com.navinSecurity.thirdSecJWT.dto;

import com.navinSecurity.thirdSecJWT.model.ServiceCategory;
import com.navinSecurity.thirdSecJWT.model.ServiceMod;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ServiceDtoCreate {
    private String name;
    private String cat;
    private String description;
    private String image;
    private Double price;
    private ServiceCategory serviceCategory;


    public ServiceMod convert(ServiceDtoCreate servDto){
        return ServiceMod.builder()
                .name(servDto.getName())
                .cat(servDto.getServiceCategory().getName())
                .description(servDto.getDescription())
                .image(servDto.getImage())
                .price(servDto.getPrice())
                .serviceCategory(getServiceCategory())
                .build();
    }

}
