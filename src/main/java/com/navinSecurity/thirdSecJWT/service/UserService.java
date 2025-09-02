package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.LoginDto;
import com.navinSecurity.thirdSecJWT.dto.PublicUser;
import com.navinSecurity.thirdSecJWT.dto.UserResponse;
import com.navinSecurity.thirdSecJWT.model.Cart;
import com.navinSecurity.thirdSecJWT.model.Role;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.repo.CartRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepo repo;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;
    @Autowired
    MyUserDetailsService userDetailsService;
    @Autowired
    private CartRepo cartRepo;

    @Override
    public List<User> getUsers() {
        return repo.findAll();
    }
    @Override
    public User getUserByFirst(String first){
        Optional<User> user=repo.findByFirst(first);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UsernameNotFoundException("Not found");
        }
    }
    @Override
    public User getUserByLast(String last){
        Optional<User> user=repo.findByLast(last);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UsernameNotFoundException("Not found");
        }
    }
    @Override
    public User getUserById(Long user_id){
        Optional<User> option=repo.findById(user_id);
        if(option.isPresent()){
            return option.get();
        }else{
            throw new RuntimeException("User not Found");
        }
    }
    @Override
    public User registerUser(User user) {
        Optional<User> getUser=repo.findByEmail(user.getEmail());
        if(getUser.isEmpty()){
            user.setPassword(passwordEncoder().encode(user.getPassword()));
            User userLevel=new User().convertUser(user);
            User _user=repo.save(userLevel);
            Cart cart=new Cart();
            cart.setUser(_user);
            Cart retCart=cartRepo.save(cart);
            _user.setCart(retCart);
            return repo.save(_user);
        }else{
            throw new RuntimeException("all ready registered");
        }

    };
    @Override
    public User registerAdmin(User user) {
        Optional<User> getUser=repo.findByEmail(user.getEmail());
        if(getUser.isEmpty()){
            User adminLevel=new User().convertAdmin(user);
            repo.save(adminLevel);
        };
        return user;
    };
    @Override
    public UserResponse logIn(LoginDto loginDto) {
        Optional<User> getUser=repo.findByEmail(loginDto.getEmail());
        if(getUser.isPresent()){
            User user=getUser.get();
            return verifyUser(user,loginDto);
        }else{
            throw new UsernameNotFoundException("not found");
        }

    }

    @Override
    public PublicUser changePassword(User user, String password) {
        String hashPassword=passwordEncoder().encode(password);
        User newPassUser=new User().changePassword(user,hashPassword);
        User userSaved=repo.save(newPassUser);
        return new PublicUser().convert(userSaved);
    }


    public UserResponse verifyUser(User user, LoginDto loginDto){
        //JWTFilter,THEn UsernamePasswordAuthentication=>in filterChain
        //SECURITY CONFIGURATION AUTHENTICATES THEN ASSIGNES A JWT
        UserDetails userDetails= userDetailsService.loadUserByUsername(user.getEmail());
        UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                loginDto.getPassword(),
                userDetails.getAuthorities()
        );
        Authentication authentication=authenticationManager.authenticate(auth);

        System.out.println("VERIFYUSER:" + user + "authentication: " + authentication);
        if(authentication.isAuthenticated()){
            String token=jwtService.generateToken(user);
            System.out.println("TOKEN: " + token);
        return new UserResponse().convert(user,token);
        };
        return null;
    }



    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
