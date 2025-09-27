package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.dto.AddressDto;
import com.navinSecurity.thirdSecJWT.model.Address;
import com.navinSecurity.thirdSecJWT.repo.AddressRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/post/{user_id}")
    public ResponseEntity<ResponseApi> saveAddress(@RequestBody AddressDto addressDto,@PathVariable(name="user_id") Long user_id){
        System.out.println("user_id:"+user_id + " : body: " + addressDto.getCountry());
        try {
            Address _address=addressService.saveAddress(addressDto,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_address,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseApi(e.getMessage(),"failed"));
        }
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<ResponseApi> getService(@PathVariable(name="user_id") Long user_id){
        try {
            Address _address=addressService.getService(user_id);
            return ResponseEntity.ok().body(new ResponseApi(_address,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    }

    @PostMapping("/update/{user_id}")
    public ResponseEntity<ResponseApi> updateAddress(@RequestBody Address address,@PathVariable(name="user_id") Long user_id){
        try {
            Address _address=addressService.updateAddress(address,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_address,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    }
}






















