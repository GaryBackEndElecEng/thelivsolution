package com.navinSecurity.thirdSecJWT.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.navinSecurity.thirdSecJWT.model.Address;
import com.navinSecurity.thirdSecJWT.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private String street;
    private String city;
    private String prov_state;
    private String country;
    private User user;

    public Address convert(AddressDto addressDto){
        return Address.builder()
                .street(addressDto.getStreet())
                .city(addressDto.getStreet())
                .prov_state(addressDto.getProv_state())
                .country(addressDto.getCountry())
                .build();
    }
}
