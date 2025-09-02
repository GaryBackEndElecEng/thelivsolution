package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.model.Cart;
import com.navinSecurity.thirdSecJWT.model.CartItem;
import com.navinSecurity.thirdSecJWT.model.Product;
import com.navinSecurity.thirdSecJWT.model.ServiceMod;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/user/cart")
public class CartController {

    @Autowired
    private final CartService cartService;


//    @PreAuthorize("hasAnyRole('ADMIN','ROLE_USER','MANAGER')")
    @GetMapping("/{user_id}")
    public ResponseEntity<ResponseApi> getCart(@PathVariable(name="user_id") Long user_id){
        try {
            System.out.println("user_id: "+user_id);
            Cart cart=cartService.getCart(user_id);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
//    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @PostMapping("/product/add/{user_id}")
    public ResponseEntity<ResponseApi> addProduct(@RequestBody Product prod,@PathVariable(name="user_id") Long user_id){
        try {
            Cart cart =cartService.addProduct(user_id,prod);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
//    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @PostMapping("/service/add/{user_id}")
    public ResponseEntity<ResponseApi> addService(@RequestBody ServiceMod serv, @PathVariable(name="user_id") Long user_id){
        try {
            Cart cart =cartService.addService(user_id,serv);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
//    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @PostMapping("/cartItem/delete/{user_id}")
    public ResponseEntity<ResponseApi> deleteItem(@RequestBody CartItem cartItem, @PathVariable(name="user_id") Long user_id){
        try {
            Cart cart =cartService.deleteItem(user_id,cartItem);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
//    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @PostMapping("/product/delete/{user_id}")
    public ResponseEntity<ResponseApi> deleteProduct(@RequestBody Product prod, @PathVariable(name="user_id") Long user_id){
        try {
            Cart cart =cartService.deleteProduct(user_id,prod);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

//    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @PostMapping("/service/delete/{user_id}")
    public ResponseEntity<ResponseApi> deleteService(@RequestBody ServiceMod serv, @PathVariable(name="user_id") Long user_id){
        try {
            Cart cart =cartService.deleteService(user_id,serv);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

};

























