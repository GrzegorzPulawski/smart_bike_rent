package com.smart_bike_rent.exception;

public class RentingIsNotExistsException extends RuntimeException{
    public RentingIsNotExistsException(String message) {
        super(message);
    }
}
