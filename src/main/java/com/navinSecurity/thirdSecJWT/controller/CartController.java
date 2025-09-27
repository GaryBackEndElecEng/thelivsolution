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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/user/cart")
public class CartController {

    @Autowired
    private final CartService cartService;


//    @PreAuthorize("hasAnyRole('ADMIN','ROLE_USER','MANAGER')")
    @GetMapping("/carts/{user_id}")
    public ResponseEntity<ResponseApi> getUsersCarts(@PathVariable(name="user_id") Long user_id){
        try {

            List<Cart> cart=cartService.getUserCarts(user_id);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
    @GetMapping("/{user_id}/{cartId}")
    public ResponseEntity<ResponseApi> getCart(
            @PathVariable(name="user_id") Long user_id,
            @PathVariable(name="cartId") Long cartId
            ){
        try {

            Cart cart=cartService.getCart(user_id,cartId);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

    @GetMapping("/cart/{user_id}/{cartId}")
    public ResponseEntity<ResponseApi> getSelectCart(
            @PathVariable(name="user_id") Long user_id,
            @PathVariable(name="cartId") Long cartId
    ){
        try {

            Cart cart=cartService.getSelectCart(user_id,cartId);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
//    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @GetMapping("/product/add/{cartId}")
    public ResponseEntity<ResponseApi> addProduct( @PathVariable("cartId") Long cartId, @RequestParam(name="prodId") Long prodId){

        try {
            Cart cart =cartService.addProduct(cartId,prodId);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
//    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @GetMapping("/service/add/{cartId}")
    public ResponseEntity<ResponseApi> addService(@PathVariable(name="cartId") Long cartId,@RequestParam(name="servId") Long servId){
        try {
            Cart cart =cartService.addService(cartId,servId);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
//    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @GetMapping("/cartItem/delete/{cartId}")
    public ResponseEntity<ResponseApi> deleteItem(@PathVariable(name="cartId") Long cartId,@RequestParam(name="cartItemId") Long cartItemId){
        try {
            Cart cart =cartService.deleteItem(cartId,cartItemId);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
//    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @GetMapping("/product/delete/{cartId}")
    public ResponseEntity<ResponseApi> deleteProduct( @PathVariable(name="cartId") Long cartId,@RequestParam(name="prodId") Long prodId){
        try {
            Cart cart =cartService.deleteProduct(cartId,prodId);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

//    @PreAuthorize("hasAnyRole('ADMIN','USER','MANAGER')")
    @GetMapping("/service/delete/{cartId}")
    public ResponseEntity<ResponseApi> deleteService(
            @PathVariable("cartId") Long cartId,
            @RequestParam(name="servId") Long servId
    ){
        try {
            Cart cart =cartService.deleteService(cartId,servId);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

    @GetMapping("/product/add/qty/{cartId}")
    public ResponseEntity<ResponseApi> addProductQuantity(@PathVariable(name="cartId") Long cartId, @RequestParam(name="prodId") Long prodId){
        try {
            Cart cart =cartService.addProductQuantity(cartId,prodId);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
    @GetMapping("/service/add/qty/{cartId}")
    public ResponseEntity<ResponseApi> addServiceQuantity(@PathVariable(name="cartId") Long cartId, @RequestParam(name="servId") Long servId){
        try {
            Cart cart =cartService.addServiceQuantity(cartId,servId);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
    @GetMapping("/service/sub/qty/{cartId}")
    public ResponseEntity<ResponseApi> subServiceQuantity(@PathVariable(name="cartId") Long cartId, @RequestParam(name="servId") Long servId){
        try {
            Cart cart =cartService.subServiceQuantity(cartId,servId);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };
    @GetMapping("/product/sub/qty/{cartId}")
    public ResponseEntity<ResponseApi> subProductQuantity(@PathVariable(name="cartId") Long cartId, @RequestParam(name="prodId") Long prodId){
        try {
            Cart cart =cartService.subProductQuantity(cartId,prodId);
            return ResponseEntity.ok().body(new ResponseApi(cart,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

};

























