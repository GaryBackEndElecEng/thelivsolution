package com.navinSecurity.thirdSecJWT.response;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.NoSuchFileException;

public class NoFileFound extends NoResourceFoundException {
    public NoFileFound(HttpMethod httpMethod, String resourcePath) {
        super(httpMethod,resourcePath);
    }
}
