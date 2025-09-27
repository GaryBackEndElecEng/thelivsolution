package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.dto.Charge;
import com.navinSecurity.thirdSecJWT.dto.ChargeDto;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class ChargeController {

    @Autowired
    private final CartService cartService;
    //ADD StripeService paymentsService ( Charge Class)
    //MISSING STEP SEND A STRIPE CLIENT KEY TO THE CLIENT (FRONTEND)
    //PAYMENT IS MADE HERE THRU THE CHARGE REQUEST
    //THE SEND CLIENTS EMAIL WITH CHARGE IF YOU WANT STRIPE TO SEND THE CLIENT AN EMAIL
    //CREATE A CALLBACK HOOK ENDPOINT AND REGISTER IT WITH STRIPE.STRIPE WILL SEND EVENYS BACK UPON ACTIVATION/COMPLETION.
    //SEVERAL ENPOINT CALLBACK CAN BE CREATED

    @GetMapping("/pre-purchase")
    public  ResponseEntity<ResponseApi>prePurchase(
            @RequestParam(name="user_id") Long user_id,
            @RequestParam(name="cartId") Long cartId ){
        try {

            Charge charge=cartService.prePurchase(user_id,cartId);
            ChargeDto chargeDto=new ChargeDto().convert(charge);
            return ResponseEntity.ok().body(new ResponseApi(chargeDto,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseApi(e.getMessage(),"failed"));
        }

    }
    @GetMapping("/purchase")
    //SHOULD BE @PostController
    public  ResponseEntity<ResponseApi>purchase(
            @RequestParam(name="user_id") Long user_id,
            @RequestParam(name="cartId") Long cartId ){
        try {
            //Charge charge=paymentService.charge(charge<=from service)//it sends an email + confermation of payment hook
            Charge charge=cartService.purchase(user_id,cartId);
            ChargeDto chargeDto=new ChargeDto().convert(charge);
            return ResponseEntity.ok().body(new ResponseApi(chargeDto,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseApi(e.getMessage(),"failed"));
        }

    }

}
