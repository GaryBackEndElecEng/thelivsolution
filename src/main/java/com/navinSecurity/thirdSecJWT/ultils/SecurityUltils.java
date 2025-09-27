package com.navinSecurity.thirdSecJWT.ultils;

import com.navinSecurity.thirdSecJWT.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityUltils {
// @PreAuthorize("hasAuthority('WRITE_PERMISSION')")

    @Autowired
    ApplicationContext context;


    public  boolean hasAuthority(String email) {
        UserDetailsService userDetailsService=context.getBean(UserDetailsService.class);
        UserDetails userDetails=userDetailsService.loadUserByUsername(email);
        if (userDetails != null && userDetails.getAuthorities() != null) {
            for (GrantedAuthority grantedAuthority:userDetails.getAuthorities()) {
                   for(String role:getRoles()){
                        if (grantedAuthority.getAuthority().equals(role)) {
                            return true;
                        }

                   }

            }
        }
        return false;
    }
    public List<String> getRoles(){
        List<String> roles=new ArrayList<>();
        roles.add(Role.ROLE_ADMIN.toString());
        roles.add(Role.ROLE_MANAGER.toString());
        return roles;
    }


    public  String getAuthorities(String email) {
        UserDetailsService userDetailsService=context.getBean(UserDetailsService.class);
        UserDetails getUserDetails= userDetailsService.loadUserByUsername(email);
        return getUserDetails.getAuthorities().stream().toList().toString();

    }
};
