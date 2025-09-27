package com.navinSecurity.thirdSecJWT.controller;

import com.navinSecurity.thirdSecJWT.dto.LoginDto;
import com.navinSecurity.thirdSecJWT.dto.PublicUser;
import com.navinSecurity.thirdSecJWT.dto.UserResponse;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.response.ResponseApi;
import com.navinSecurity.thirdSecJWT.service.JwtService;
import com.navinSecurity.thirdSecJWT.service.UserService;
import com.navinSecurity.thirdSecJWT.ultils.SecurityUltils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController 
{
    @Autowired
    private UserService service;
    @Autowired
    private JwtService jwtService;
    @Autowired
    SecurityUltils securityUltils;

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER','MANAGER)")
    @GetMapping("/users")
    ResponseEntity<ResponseApi> getUsers(){
        try {
            List<PublicUser> users=service.getSafeUsers();
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

    @RequestMapping(path="/login/post",method=RequestMethod.POST)
    public ResponseEntity<ResponseApi> logIn(@RequestBody LoginDto loginDto, HttpServletResponse res){
        try {
            UserResponse userAndToken=service.logIn(loginDto);
            String getAuthorities=securityUltils.getAuthorities(loginDto.getEmail());
            System.out.println("GETAUTHORITIES: "+ getAuthorities);
            res.addHeader("Authorization","Bearer " + userAndToken.getJwt());
            return new ResponseEntity<>(new ResponseApi(userAndToken,"success"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"),HttpStatus.BAD_REQUEST);
        }
    }
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER','MANAGER)")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseApi> registerUser(@PathVariable(name="userId") Long userId){
        try {
            User user=service.getUserById(userId);
            UserResponse _user=new UserResponse().convert(user,"");
            return new ResponseEntity<>(new ResponseApi(_user,"success"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"),HttpStatus.BAD_REQUEST);
        }
    };

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER','MANAGER)")
    @GetMapping("/user/{user_id}/{oldPassword}/{newPassword}")
    public ResponseEntity<ResponseApi> changePassword(
            @PathVariable Long user_id,
            @PathVariable(name="oldPassword") String oldPassword,
            @PathVariable(name="newPassword") String newPassword
    ){
        try {
            UserResponse _user=service.changePassword(user_id,oldPassword,newPassword);
            return new ResponseEntity<>(new ResponseApi(_user,"success"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi(e.getMessage(),"failed"),HttpStatus.BAD_REQUEST);
        }
    };

    @GetMapping("/logout/{user_id}")
    public ResponseEntity<ResponseApi> logout(@PathVariable(name="user_id") Long user_id, HttpServletRequest request,HttpServletResponse response){
        System.out.println("LOGGED OUT" + user_id);
        String res=jwtService.invalidateTokenClear(request,response);
        return ResponseEntity.ok().body(new ResponseApi("logout","success"));
    }


    
};

























