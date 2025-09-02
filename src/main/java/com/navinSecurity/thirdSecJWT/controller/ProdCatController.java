package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.model.ProductCategory;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.ProdCatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category/products")
public class ProdCatController {

    @Autowired
    private final ProdCatService prodCatService;
    private String name;

    @GetMapping
    public ResponseEntity<ResponseApi> getAllCategory(){
        try {
            List<ProductCategory> prodCats=prodCatService.getAllCategory();
            return ResponseEntity.ok().body(new ResponseApi(prodCats,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseApi> findByName(@PathVariable(name="name") String name){
        try {
            ProductCategory prodCat=prodCatService.findByName(name);
            return ResponseEntity.ok().body(new ResponseApi(prodCat,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

    @PutMapping("/update")
    public ResponseEntity<ResponseApi> updateCategory(@RequestBody ProductCategory prodCat){
        try {
            ProductCategory retProdCat=prodCatService.updateCategory(prodCat);
            return ResponseEntity.ok().body(new ResponseApi(retProdCat,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

    @PostMapping("/post")
    public ResponseEntity<ResponseApi> postCategory(@RequestBody ProductCategory prodCat){
        try {
            ProductCategory retProdCat=prodCatService.post(prodCat);
            return ResponseEntity.ok().body(new ResponseApi(retProdCat,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> findById(@PathVariable(name="id") Long id){
        try {
            ProductCategory prodCat=prodCatService.findById(id);
            return ResponseEntity.ok().body(new ResponseApi(prodCat,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

};





















