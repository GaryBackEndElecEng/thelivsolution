package com.navinSecurity.thirdSecJWT.config;

import com.navinSecurity.thirdSecJWT.service.JwtService;
import com.navinSecurity.thirdSecJWT.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.function.ServerRequest;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

//@Filter(name="JwtFilter")
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    ApplicationContext context;
    @Autowired
    MyUserDetailsService userDetailService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Bearer token
        String getAuth= request.getHeader("Authorization");
        String token=null;
        String email=null;
        if(getAuth !=null && getAuth.startsWith("Bearer")){
            token=getAuth.split("")[1].trim();
            email=jwtService.extractEmail(token);
//            System.out.println(" EXTRACTED EMAIL: " + email);//works
        }
        if(email !=null && SecurityContextHolder.getContext().getAuthentication()==null){
            //NOT AUTHENTICATED
//            System.out.println(" jWTfILTER:EXTRACTED EMAIL: " + email  +"PASSWORD:" );//works
                UserDetails userDetails= context.getBean(MyUserDetailsService.class).loadUserByUsername(email);
            if(jwtService.validateToken(token,userDetails)){
                //Do next Filter
//                USERDETAILS.GETAUTHORITIES() CRITICAL ( VALIDATES LOGIN)

                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                        );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//ADDING TOKEN TO HEADER:AUTHENTICATION
                SecurityContextHolder.getContext().setAuthentication(authToken);// INSERTING RENEW TOKEN

//                System.out.println("SecurityContextHolder.getContext(): " + SecurityContextHolder.getContext());

                //SecurityContextHolder.getContext(): SecurityContextImpl [Authentication=UsernamePasswordAuthenticationToken
                // [Principal=masterconnect919@gmail.com, Credentials=[PROTECTED],
                // Authenticated=false,
                // Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null], Granted Authorities=[]]]

            addTokenToHeader(response,token,userDetails);
            };
            filterChain.doFilter(request,response);

        };
        filterChain.doFilter(request,response); //DOING NEXT FILTER

    };

    public void addTokenToHeader(HttpServletResponse response,String token,UserDetails userDetails){
        boolean isValid=jwtService.validateToken(token,userDetails);
        String bearer="Bearer " + token;

            response.setHeader("Authentication",bearer);


    };

    //end
};
