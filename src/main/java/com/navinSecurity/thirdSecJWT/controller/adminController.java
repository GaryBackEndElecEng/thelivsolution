package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.dto.AdminLoginDto;
import com.navinSecurity.thirdSecJWT.dto.UserResponse;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.AdminService;
import com.navinSecurity.thirdSecJWT.service.UserService;
import com.navinSecurity.thirdSecJWT.ultils.SecurityUltils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth/admin")
@RequiredArgsConstructor
public class adminController {

    @Autowired
    private final AdminService adminService;
    @Autowired
    SecurityUltils securityUltils;
    @Autowired
    private final UserService userService;

    @PostMapping("/login/post")
    public ResponseEntity<ResponseApi> login(@RequestBody AdminLoginDto adminLoginDto){
        try {
            UserResponse user=adminService.login(adminLoginDto);
            String getAuths=securityUltils.getAuthorities(adminLoginDto.getEmail());
            System.out.println("GETAUTHORITEIS: " +getAuths);
            return ResponseEntity.ok().body(new ResponseApi(user,getAuths));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }
    @GetMapping("/users/{owner_id}")
    public ResponseEntity<ResponseApi> getUsers(@PathVariable(name="owner_id") Long owner_id){
        try {
            List<User> users=userService.getAdminUsers(owner_id);
            List<UserResponse> retUsers=userService.converUsersResponse(users);
            return ResponseEntity.ok().body(new ResponseApi(retUsers,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }
    @PostMapping("/post/{owner_id}")
    public ResponseEntity<ResponseApi> postUser(
            @PathVariable(name="owner_id") Long owner_id,
            @RequestBody User newuser
            ){
        try {
            UserResponse user=userService.adminPostNewUser(newuser,owner_id);

            return ResponseEntity.ok().body(new ResponseApi(user,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }
    @DeleteMapping("/user/delete/{owner_id}")
    public ResponseEntity<ResponseApi> deleteUser(
            @PathVariable(name="owner_id") Long owner_id,
            @RequestParam("user_id") Long user_id
    ){
        try {
            Long userId=userService.deleteUser(owner_id,user_id);
            return ResponseEntity.ok().body(new ResponseApi(userId,"success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseApi(e.getMessage(),e.getCause().toString()));
        }
    }







}



















