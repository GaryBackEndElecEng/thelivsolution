package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.LoginDto;
import com.navinSecurity.thirdSecJWT.dto.PublicUser;
import com.navinSecurity.thirdSecJWT.dto.UserResponse;
import com.navinSecurity.thirdSecJWT.model.Address;
import com.navinSecurity.thirdSecJWT.model.Cart;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.repo.AddressRepo;
import com.navinSecurity.thirdSecJWT.repo.CartRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import com.navinSecurity.thirdSecJWT.response.NotAuthorizedException;
import com.navinSecurity.thirdSecJWT.ultils.SecurityUltils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
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
    @Autowired
    public final SecurityUltils securityUltils;
    @Autowired
    public final AddressRepo addressRepo;


    @Override
    public List<PublicUser> getSafeUsers() {
       List<User> users=repo.findAll();
       return convertSafeUsers(users);
    }

    public List<PublicUser> convertSafeUsers(List<User> users){
        List<PublicUser> pubUsers=new ArrayList<>();
        users.forEach(user->{
            pubUsers.add(new PublicUser().convert(user));
        });
        return pubUsers;
    }
    @Override
    public List<User> getAdminUsers(Long user_id) {
        Optional<User> user=repo.findById(user_id);
        if(user.isPresent()){
            if(this.hasAuthorities(user.get().getEmail())){
                return repo.findAll();

            }else{
                throw new AuthorizationDeniedException("ONLy ADMIN");
            }
        }else{
            throw new RuntimeException("no user");
        }
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
            throw new UsernameNotFoundException("User not found");
        }
    }
    @Override
    public User registerUser(User user) {
        Optional<User> getUser=repo.findByEmail(user.getEmail());
        List<Cart> carts=new ArrayList<>();
        if(getUser.isEmpty()){
            user.setPassword(passwordEncoder().encode(user.getPassword()));
            user=repo.save(user);
            Cart cart=new Cart();
            Address address=new Address();
            address.setUser(user);
            address=addressRepo.save(address);
            user.setAddress(address);
            cart.setUser(user);
            Cart retCart=cartRepo.save(cart);
            carts.add(retCart);
            user.setCarts(new HashSet<Cart>(carts));
            User userLevel=new User().convertUser(user);
            User _user=repo.save(userLevel);
            return repo.save(_user);
        }else{
            throw new EntityNotFoundException("User is already registered");
        }

    };
    @Override
    public User registerAdmin(User user,Long user_id) {
        Optional<User> isOwner=repo.findById(user_id);
        if(isOwner.isPresent()){
            if(this.hasAuthorities(isOwner.get().getEmail())){
                Optional<User> getUser=repo.findByEmail(user.getEmail());
                if(getUser.isEmpty()){
                    User adminLevel=new User().convertAdmin(user);
                    repo.save(adminLevel);
                };
                return user;
            }else{
                throw new AuthorizationDeniedException(" no admin rights");
            }

        }else{
            throw new RuntimeException("no user is present");
        }
    };


    @Override
    public UserResponse logIn(LoginDto loginDto) {
        Optional<User> getUser=repo.findByEmail(loginDto.getEmail());
        if(getUser.isPresent()){
            User user=getUser.get();
            user.setUpdates(loginDto.getUpdates());
            return verifyUser(user,loginDto);
        }else{
            throw new UsernameNotFoundException("not found");
        }

    }

    @Override
    public UserResponse changePassword(Long user_id, String oldPassword,String newPassword) {
        Optional<User> option=repo.findById(user_id);
        if(option.isPresent()){
            if(passwordMatch(oldPassword,option.get().getPassword())){
                String hashPassword=passwordEncoder().encode(newPassword);
                User newPassUser=option.get().changePassword(option.get(),hashPassword);
                User userSaved=repo.save(newPassUser);
                return userSaved.convert(userSaved);

            }else{
                throw new CompromisedPasswordException("password did not match");
            }
        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public Long deleteUser(Long ownerId,Long user_id) {
        Optional<User> getuser=repo.findById(ownerId);
        Optional<User> deleteUser=repo.findById(user_id);
        if(getuser.isPresent()){
            if(hasAuthorities(getuser.get().getEmail()) && deleteUser.isPresent()){
                repo.delete(deleteUser.get());
                return user_id;
            }else{
                throw new AuthorizationDeniedException("ONLY ADMIN ALLOWED");
            }
        }else{
            throw new UsernameNotFoundException("Owner not found");
        }
    }

    @Override
    public UserResponse adminPostNewUser(User user, Long owner_id) {
        Optional<User> isOwner=repo.findById(owner_id);
        if(isOwner.isPresent()){
            if(hasAuthorities(isOwner.get().getEmail())){
                Cart cart=new Cart();
                cart=cartRepo.save(cart);
                user.setPassword(passwordEncoder().encode(user.getPassword()));
                user=repo.save(user);
                cart.setUser(user);
                List<Cart> carts=new ArrayList<>();
                carts.add(cart);
                user.setCarts(new HashSet<>(carts));
                Address address=new Address();
                address=addressRepo.save(address);
                user.setAddress(address);
                address.setUser(user);
                addressRepo.save(address);
                cartRepo.save(cart);
                repo.save(user);
                return new UserResponse().convert(user,"");

            }else{
                throw new NotAuthorizedException("ONLY ADMIN ALLOWED");
            }
        }else{
            throw new RuntimeException("No Owner present");
        }
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

        if(authentication.isAuthenticated()){
            String token=jwtService.generateToken(user);
        return new UserResponse().convert(user,token);
        }else{
            throw new NotAuthorizedException("verified user failed");
        }
    }


    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public Boolean passwordMatch(String raw,String encodedPass){
        return passwordEncoder().matches(raw,encodedPass);
    }

    public Boolean hasAuthorities(String email){
        return securityUltils.hasAuthority(email);
    }


    public List<UserResponse> converUsersResponse(List<User> users) {
        List<UserResponse> retUsers=new ArrayList<>();
        users.forEach(user->{
            UserResponse newUser=new UserResponse().convert(user,"");
            retUsers.add(newUser);
        });
        return retUsers;
    }
}
