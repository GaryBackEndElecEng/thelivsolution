package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.AddressDto;
import com.navinSecurity.thirdSecJWT.model.Address;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.repo.AddressRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {

    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final AddressRepo addressRepo;

    @Override
    public Address saveAddress(AddressDto addressDto, Long user_id) {
        Optional<User> option=userRepo.findById(user_id);
        if(option.isPresent()){
            Address retAddress=addressRepo.save(addressDto.convert(addressDto));
//           User user=userRepo.save(option.get());
            retAddress.setUser(option.get());
            return addressRepo.save(retAddress);
        }else{
            throw new NoSuchElementException(" no user");
        }

    }

    @Override
    public Address getService(Long user_id) {
        Optional<User> option=userRepo.findById(user_id);
        if(option.isPresent()){
            return option.get().getAddress();
        }else{
            throw new RuntimeException("no user");
        }

    }

    @Override
    public Address updateAddress(Address address,Long user_id) {
        Optional<User> option=userRepo.findById(user_id);
        if(option.isPresent()){
            address.setUser(option.get());
            return addressRepo.save(address);
        }else{
            throw new RuntimeException("not found");
        }
    }
}
