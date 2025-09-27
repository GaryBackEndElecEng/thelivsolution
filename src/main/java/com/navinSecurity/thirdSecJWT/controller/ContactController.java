package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.model.Contact;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {
    @Autowired
    private final ContactService contactService;


    @GetMapping("/{user_id}")
    public ResponseEntity<ResponseApi> getAllAssociates(@PathVariable(name="user_id") Long user_id){
        try {
            List<Contact>contacts=contactService.getAllAssociates(user_id);
            return ResponseEntity.ok().body(new ResponseApi(contacts,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }

    @GetMapping("/{user_id}/{contact_id}")
    public ResponseEntity<ResponseApi> getAssociate(
            @PathVariable(name="user_id") Long user_id,
            @PathVariable(name="contact_id") Long contact_id
    ){
        try {
            Contact contact=contactService.getAssociate(user_id,contact_id);
            return ResponseEntity.ok().body(new ResponseApi(contact,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }

    @DeleteMapping("/delete/{user_id}/{contact_id}")
    public ResponseEntity<ResponseApi> deleteAssociate(
            @PathVariable(name="user_id") Long user_id,@PathVariable(name="contact_id") Long contact_id
    ){
        try {
            Long id=contactService.deleteAssociate(user_id,contact_id);
            return ResponseEntity.ok().body(new ResponseApi(id,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }

    @PostMapping("/post/{user_id}")
    public ResponseEntity<ResponseApi> postAssociate(
            @RequestBody Contact contact,@PathVariable(name="user_id") Long user_id
    ){
        try {
            System.out.println("HITTTT: "+ user_id + " : " + contact.getDepartment());
            Contact _contact=contactService.postAssociate(contact,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_contact,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }

    @PostMapping("/update/{user_id}")
    public ResponseEntity<ResponseApi> updateAssociate(
            @RequestBody Contact contact,@PathVariable(name="user_id") Long user_id
    ){
        try {
            Contact _contact=contactService.updateAssociate(contact,user_id);
            return ResponseEntity.ok().body(new ResponseApi(_contact,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }
}
























