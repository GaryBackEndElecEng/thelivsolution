package com.navinSecurity.thirdSecJWT.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ServiceDtoCreate {
    private String name;
    private String cat;
    private String desc;
    private String image;

}
