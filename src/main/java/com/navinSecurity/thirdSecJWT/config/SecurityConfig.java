package com.navinSecurity.thirdSecJWT.config;

import com.navinSecurity.thirdSecJWT.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity(debug=true) //I WANT TO CONTROL THE FLOW
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${frontend}")
    String frontend;
    @Value("${frontendTwo}")
    String frontendTwo;

    @Autowired
    MyUserDetailsService userDetailsService;
    @Autowired
    private JwtFilter jwtFilter;

//    @Bean
//    UserDetailsService userDetailsServiceBean(){
//        return userDetailsService;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                .requestMatchers("/api/**")//ALLOWS NON USER REGISTER,LOGIN
                .permitAll()
                .anyRequest().authenticated()
        );
        //        http.formLogin(Customizer.withDefaults());//implement form log-in
//        http.httpBasic(Customizer.withDefaults());//invokes basic authentication @fetch
        //STATELESS GIVES YOU A NEW SESSION ID. THIS WORKS FOR POSTMAN BUT WITH LOGIN FORM, IT RETURNS A NEW SESSIONID
        // FORCING THE LOGIN PROMPT TO DISPLAY AGAIN. SO DISABLE THE LOGIN FORM
        http.sessionManagement(session->session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class)
                ;
        http.authenticationManager(
                authenticationManager()
        );



        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(frontend,"http://localhost:4200","http://localhost:9090"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("Content-Type", "Credentials"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    };


//    @Bean
//    //ALLOCATED  FOR AUTHENTICATION ON LOGIN && TOKEN
//    public AuthenticationProvider authenticationProvider(){
//        //Authentication Provider is an Interface. By this we need a class to serve Authenication Provider;
//        DaoAuthenticationProvider provider= new DaoAuthenticationProvider(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());//THIS REMOVES THE NEED OF {noop}password
//        return provider;
//    };

    @Bean
    //INTERFACE
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    };


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }




};
