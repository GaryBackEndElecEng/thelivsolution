package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    String secretKey ;
    public JwtService(){
        try{
            KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
         SecretKey   sk= keyGen.generateKey();
         secretKey= Base64.getEncoder().encodeToString(sk.getEncoded());
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    };

    public String extractEmail(String token) {
        return extractClaim(token,Claims::getSubject);

    };

    public  boolean validateToken(String token, UserDetails userDetails) {
        final String email=extractEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    };

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    private String extractIssuer(String token){
        return extractClaim(token,Claims::getIssuer);
    }


    public String generateToken(User user) {
        System.out.println("USER: " + user);
        Map<String,Object> claims= new HashMap<>();
        return builder()
                .claims()
                .add(claims)
                .subject(user.getEmail().trim())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .and()
                .signWith(getKey(secretKey.trim()))
                .compact();
    }

    private SecretKey getKey(String secretKey) {
        byte[] key= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    };


    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims=extractAllClaims(token);
        System.out.println("CLAIMS: "+ claims);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey(secretKey))
                .build().parseSignedClaims(token).getPayload();
    }


}
