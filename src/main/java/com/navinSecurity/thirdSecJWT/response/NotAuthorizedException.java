package com.navinSecurity.thirdSecJWT.response;

import org.springframework.security.authorization.AuthorizationDeniedException;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String message){
    AuthorizationDeniedException auth=new AuthorizationDeniedException(message);
        throw new RuntimeException(auth);
    }
}
