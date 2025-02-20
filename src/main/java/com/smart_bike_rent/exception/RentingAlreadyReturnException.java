package com.smart_bike_rent.exception;

public class RentingAlreadyReturnException extends RuntimeException{
    public RentingAlreadyReturnException(String message) {
        super(message);
    }
}
