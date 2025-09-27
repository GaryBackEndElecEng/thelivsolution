package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.AdminLoginDto;
import com.navinSecurity.thirdSecJWT.dto.LoginDto;
import com.navinSecurity.thirdSecJWT.dto.UserResponse;
import com.navinSecurity.thirdSecJWT.model.Cart;
import com.navinSecurity.thirdSecJWT.model.Role;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.repo.CartRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService{

    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final CartRepo cartRepo;

    @Value("${adminEmail}")
    String  adminEmail;
    @Value("${adminPassword}")
    String adminPassword;
    @Value("${adminRole}")
    String adminRole;



    @Override
    public UserResponse login(AdminLoginDto adminLoginDto) {
        Optional<User> getUser=userRepo.findByEmail(adminLoginDto.getEmail());
        if(getUser.isPresent()){
            User user=getUser.get();
            LoginDto logInDto=new LoginDto(user.getEmail(),adminLoginDto.getPassword(),false);
            if(this.isSuperAdmin(adminLoginDto)){
                // admin add cart if empty
                this.addUserCart(getUser.get());
                return userService.verifyUser(getUser.get(),logInDto);
            }else{
                //manager add cart if empty
                this.addUserCart(getUser.get());
                return userService.verifyUser(getUser.get(),logInDto);
            }

        }else{
            throw new AuthorizationDeniedException("not authorized");
        }

    }

    public boolean isSuperAdmin(AdminLoginDto adminLoginDto){
        AdminLoginDto adminCred=this.loginBuilder();
        return adminCred.getEmail().equals(adminLoginDto.getEmail()) &&
                adminCred.getPassword().equals(adminLoginDto.getPassword()) &&
                adminCred.getRole().equals(adminLoginDto.getRole());
    }

    public AdminLoginDto loginBuilder(){
        return AdminLoginDto.builder()
                .email(adminEmail)
                .password(adminPassword)
                .role(Role.valueOf(adminRole))
                .build();
    }

    public void addUserCart(User user){
        List<Cart> carts=user.getCarts().stream().toList();
        if(carts.isEmpty()){
            Cart cart =new Cart();
            cart.setUser(user);
            cart=cartRepo.save(cart);
            user.getCarts().add(cart);
            userRepo.save(user);
        }
    }





}
