package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.dto.ProductDtoCreate;
import com.navinSecurity.thirdSecJWT.model.Product;
import com.navinSecurity.thirdSecJWT.repo.ProductRepo;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.ProductService;
import com.navinSecurity.thirdSecJWT.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;
    @Autowired
    UserService userService;

        //WILL BE ADMIN
    @PostMapping("/post/{user_id}/{id}")
    public ResponseEntity<ResponseApi> addProduct(
            @RequestBody ProductDtoCreate productDto,
            @PathVariable(name="id") Long id,
            @PathVariable(name="user_id") Long user_id
    ){
//        System.out.println("INPUT::: " + productDto);//works
        try {

            Product _prod=productService.saveProduct(productDto,id,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_prod,"success"));
        } catch (Exception e) {
           return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.BAD_REQUEST);
        }
    }
    //ADMIN
    //ONLY QTY UPDATE FOR USER
//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{user_id}")
    public ResponseEntity<ResponseApi> updateProduct(@RequestBody Product product,@PathVariable(name="user_id") Long user_id){
        try {

            Product _prod=productService.updateProduct(product,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_prod,"success:"));
        } catch (Exception e) {
            return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{user_id}/{prodId}")
    public ResponseEntity<ResponseApi> deleteProduct(@PathVariable(name="prodId") Long prodId,@PathVariable(name="user_id") Long user_id){
        try {
            String _msg=productService.deleteProduct(prodId,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_msg,"success"));
        } catch (Exception e) {
            return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<ResponseApi> getAllProduct(){
        try {
            List<Product> prods=productService.getAllProducts();
            return ResponseEntity.ok().body(new ResponseApi(prods,"success"));
        } catch (Exception e) {
           return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{prodId}")
    public ResponseEntity<ResponseApi> getProduct(@PathVariable(name="prodId") Long prodId){
        try {
            Product prod=productService.getProduct(prodId);
            return ResponseEntity.ok().body(new ResponseApi(prod,"success"));
        } catch (Exception e) {
           return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/cat/{cat}")
    public ResponseEntity<ResponseApi> getProdCat(@PathVariable(name="cat") String cat){
        try {
            List<Product> prod=productService.findByCat(cat);
            return ResponseEntity.ok().body(new ResponseApi(prod,"success"));
        } catch (Exception e) {
            return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseApi> getProdName(@PathVariable(name="name") String name){
        try {
            Product prod=productService.findProduct(name);
            return ResponseEntity.ok().body(new ResponseApi(prod,"success"));
        } catch (Exception e) {
            return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.NOT_FOUND);
        }
    }
}



























