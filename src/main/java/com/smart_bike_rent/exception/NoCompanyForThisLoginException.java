package com.smart_bike_rent.exception;

public class NoCompanyForThisLoginException extends RuntimeException{
    public NoCompanyForThisLoginException(String message) {
        super(message);
    }
}
