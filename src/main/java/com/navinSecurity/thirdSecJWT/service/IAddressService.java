package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.AddressDto;
import com.navinSecurity.thirdSecJWT.model.Address;

public interface IAddressService {
    Address saveAddress(AddressDto address, Long user_id);
    Address getService(Long user_id);
    Address updateAddress(Address address,Long user_id);


}
