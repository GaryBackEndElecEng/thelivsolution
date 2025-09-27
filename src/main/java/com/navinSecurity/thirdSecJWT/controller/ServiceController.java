package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.dto.ServiceDtoCreate;
import com.navinSecurity.thirdSecJWT.model.Product;
import com.navinSecurity.thirdSecJWT.model.ServiceMod;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.ServService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    @Autowired
    private final ServService servService;

    @PostMapping("/post/{user_id}/{id}")
    public ResponseEntity<ResponseApi> addService(
            @RequestBody ServiceDtoCreate serviceDto,
            @PathVariable(name="user_id") Long user_id,
            @PathVariable(name="id") Long id
    ){
        try {

            ServiceMod _serv=servService.saveService(serviceDto,user_id,id);
            return ResponseEntity.ok().body(new ResponseApi(_serv,"success"));
        } catch (Exception e) {
            return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.BAD_REQUEST);
        }
    };
    @PostMapping("/update/{user_id}")
    public ResponseEntity<ResponseApi> updateService(@RequestBody ServiceMod service,@PathVariable Long user_id){
        try {
            ServiceMod _prod=servService.updateService(service,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_prod,"success"));
        } catch (Exception e) {
            return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{user_id}/{servId}")
    public ResponseEntity<ResponseApi> deleteService(@PathVariable(name="servId") Long servId,@PathVariable(name="user_id") Long user_id){
        try {
            String _msg=servService.deleteService(servId,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_msg,"success"));
        } catch (Exception e) {
            return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public ResponseEntity<ResponseApi> getAllServices(){
        try {
            List<ServiceMod> prods=servService.getAllServices();
            return ResponseEntity.ok().body(new ResponseApi(prods,"success"));
        } catch (Exception e) {
            return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.BAD_REQUEST);
        }
    };
    @GetMapping("/{servId}")
    public ResponseEntity<ResponseApi> getProduct(@PathVariable(name="servId") Long servId){
        try {
            ServiceMod prod=servService.getService(servId);
            return ResponseEntity.ok().body(new ResponseApi(prod,"success"));
        } catch (Exception e) {
            return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.NOT_FOUND);
        }
    };
    @GetMapping("/cat/{cat}")
    public ResponseEntity<ResponseApi> getServCat(@PathVariable(name="cat") String cat){
        try {
            List<ServiceMod> prod=servService.findByCat(cat);
            return ResponseEntity.ok().body(new ResponseApi(prod,"success"));
        } catch (Exception e) {
            return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };
    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseApi> getServName(@PathVariable(name="name") String name){
        try {
            ServiceMod prod=servService.findService(name);
            return ResponseEntity.ok().body(new ResponseApi(prod,"success"));
        } catch (Exception e) {
            return  new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"), HttpStatus.NOT_FOUND);
        }
    }

}






























