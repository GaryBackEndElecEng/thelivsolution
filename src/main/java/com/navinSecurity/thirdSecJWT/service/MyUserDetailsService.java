package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.Role;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service

public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepo repo;

    public MyUserDetailsService(UserRepo repo){
        this.repo=repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> option=this.repo.findByEmail(email);
        if((option.isEmpty())){
            System.out.println("USER NOT FOUND");
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
        User user=option.get();
        System.out.println("USERPRINCIPAL AFTER: "+ option.get());
        Collection<? extends GrantedAuthority>collRole=convertAuthority(user.getRole());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(collRole.stream().toList()).build();


    };
    public Collection<? extends GrantedAuthority> convertAuthority(Role role){
        String _roles=String.valueOf(role);
        List<GrantedAuthority> grantedAuthorities= AuthorityUtils.createAuthorityList(_roles);
        return new ArrayList<>(grantedAuthorities);
    }
}
