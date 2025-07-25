package com.smart_bike_rent.security.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException{
    private final HttpStatus code;
    public AppException(String message, HttpStatus code){
        super(message);
        this.code = code;
    }

    public HttpStatus getCode() {
        return code;
    }
}
