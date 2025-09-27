package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.dto.PostAffiliateDto;
import com.navinSecurity.thirdSecJWT.model.Affiliate;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.AffiliateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/affiliates")
public class AffiliateController {

    @Autowired
    private final AffiliateService affiliateService;
    @GetMapping("/{user_id}")
    public ResponseEntity<ResponseApi>getallAffiliates(@PathVariable(name="user_id") Long user_id){
        try {
            return ResponseEntity.ok().body(new ResponseApi(affiliateService.getallAffiliates(user_id),"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }
    @GetMapping("/affiliate/{affiliate_id}")
    public ResponseEntity<ResponseApi>findAffiliateById(@PathVariable("affiliate_id") Long affiliate_id){
        try {
            Affiliate affiliate=affiliateService.findAffiliateById(affiliate_id);
            return ResponseEntity.ok().body(new ResponseApi(affiliate,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }
    @GetMapping("/co/{name}")
    public ResponseEntity<ResponseApi>findAffiliateByCo(@PathVariable("name") String name){
        try {
            Affiliate affiliate=affiliateService.findAffiliateByCo(name);
            return ResponseEntity.ok().body(new ResponseApi(affiliate,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }
    @PostMapping("/update/{user_id}")
    public ResponseEntity<ResponseApi>updateAffiliate(@RequestBody Affiliate affiliate,@PathVariable("user_id") Long user_id){
        try {
            Affiliate _affiliate=affiliateService.updateAffiliate(affiliate,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_affiliate,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }
    @PostMapping("/post/{user_id}")
    public ResponseEntity<ResponseApi>postAffiliate(@RequestBody Affiliate affiliate, @PathVariable(name="user_id") Long user_id){
        try {
//            Affiliate inAffiliate=affiliate.postConvert(affiliate);
            Affiliate _affiliate=affiliateService.postAffiliate(affiliate,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_affiliate,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }
    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<ResponseApi>deleteAffiliate(@RequestParam(name="affiliate_id") Long affiliate_id,@PathVariable("user_id") Long user_id){
        try {
            Long _affiliate=affiliateService.deleteAffiliate(affiliate_id,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_affiliate,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }




















}
