package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.model.ProductCategory;
import com.navinSecurity.thirdSecJWT.model.ServiceCategory;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.ServCatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category/services")
public class ServCatController {

    @Autowired
    private ServCatService servCatService;

    @GetMapping
    public ResponseEntity<ResponseApi> getAllCategory(){
        try {
            List<ServiceCategory> prodCats=servCatService.getAllCategory();
            return ResponseEntity.ok().body(new ResponseApi(prodCats,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseApi> findByName(@PathVariable(name="name") String name){
        try {
            ServiceCategory prodCat=servCatService.findByName(name);
            return ResponseEntity.ok().body(new ResponseApi(prodCat,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

    @PutMapping("/update")
    public ResponseEntity<ResponseApi> updateCategory(@RequestBody ServiceCategory prodCat){
        try {
            ServiceCategory retProdCat=servCatService.updateCategory(prodCat);
            return ResponseEntity.ok().body(new ResponseApi(retProdCat,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

    @PostMapping("/post")
    public ResponseEntity<ResponseApi> postCategory(@RequestBody ServiceCategory prodCat){
        try {
            ServiceCategory retProdCat=servCatService.post(prodCat);
            return ResponseEntity.ok().body(new ResponseApi(retProdCat,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> findById(@PathVariable(name="id") Long id){
        try {
            ServiceCategory prodCat=servCatService.findById(id);
            return ResponseEntity.ok().body(new ResponseApi(prodCat,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseApi(e.getMessage(),"failed"));
        }
    };





}
