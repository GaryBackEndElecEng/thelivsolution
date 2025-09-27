package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.model.TaxModel;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tax")
@RequiredArgsConstructor
public class TaxmodelController {

    @Autowired
    private final TaxService taxService;

    @GetMapping
    ResponseEntity<ResponseApi> findAll(){
        try {
            return ResponseEntity.ok().body(new ResponseApi(taxService.getAllTaxModels(),"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi(e,"failed"));
        }
    }
    @GetMapping("/{country}/{prov_state}")
    ResponseEntity<ResponseApi> getTaxModelByCountryState(
            @PathVariable(name="country") String country,
            @PathVariable(name="prov_state") String prov_state){
        try {
            TaxModel taxmodel=taxService.getTaxModelByCountry(country,prov_state);
            return ResponseEntity.ok().body(new ResponseApi(taxmodel,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi(e,"failed"));
        }
    }

    @PostMapping("/post/{user_id}")
    ResponseEntity<ResponseApi>postTaxModel(@RequestBody TaxModel taxModel, @PathVariable(name="user_id") Long user_id){
        try {
            TaxModel taxmodel=taxService.postTaxModel(taxModel,user_id);
            return ResponseEntity.ok().body(new ResponseApi(taxmodel,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseApi(e,"failed"));
        }
    }
    @PostMapping("/update/{user_id}")
    ResponseEntity<ResponseApi>updateTaxModel(@RequestBody TaxModel taxModel, @PathVariable(name="user_id") Long user_id){
        try {
            TaxModel taxmodel=taxService.updateTaxModel(taxModel,user_id);
            return ResponseEntity.ok().body(new ResponseApi(taxmodel,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseApi(e,"failed"));
        }
    }
    @DeleteMapping("/delete/{user_id}/{id}")
    ResponseEntity<ResponseApi>deleteTaxModel(@PathVariable(name="user_id") Long user_id,@PathVariable(name="id") Long id){
        try {
            Long taxId=taxService.deleteTaxModel(id,user_id);
            return ResponseEntity.ok().body(new ResponseApi(taxId,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseApi(e,"failed"));
        }
    }

}




























