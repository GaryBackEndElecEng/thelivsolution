package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.dto.LoginDto;
import com.navinSecurity.thirdSecJWT.dto.PublicUser;
import com.navinSecurity.thirdSecJWT.dto.UserResponse;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController 
{
    @Autowired
    private UserService service;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER','MANAGER)")
    @GetMapping("/users")
    ResponseEntity<ResponseApi> getUsers(){
        try {
            List<User> users=service.getUsers();
            return new ResponseEntity<>(new ResponseApi(users,"success"),HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseApi> registerUser(@RequestBody User user){
        try {
            UserResponse _user=new UserResponse().convert(service.registerUser(user),"");
            return new ResponseEntity<>(new ResponseApi(_user,"success"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"),HttpStatus.BAD_REQUEST);
        }
    };

    @PostMapping("/login/post")
    public ResponseEntity<ResponseApi> logIn(@RequestBody LoginDto loginDto, HttpServletResponse res){
        try {
            UserResponse userAndToken=service.logIn(loginDto);
            res.addHeader("Authorization","Bearer " + userAndToken.getJwt());
            return new ResponseEntity<>(new ResponseApi(userAndToken,"success"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"),HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER','MANAGER)")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseApi> registerUser(@PathVariable(name="userId") Long userId){
        try {
            User user=service.getUserById(userId);
            PublicUser _user=new PublicUser().convert(user);
            return new ResponseEntity<>(new ResponseApi(_user,"success"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"),HttpStatus.BAD_REQUEST);
        }
    };

    @PreAuthorize("hasAnyRole('ADMIN', 'USER','MANAGER)")
    @PostMapping("/user/{password}")
    public ResponseEntity<ResponseApi> changePassword(@RequestBody User user,@PathVariable(name="password") String password){
        try {
            PublicUser _user=service.changePassword(user,password);
            return new ResponseEntity<>(new ResponseApi(_user,"success"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"),HttpStatus.BAD_REQUEST);
        }
    };


    
};

























