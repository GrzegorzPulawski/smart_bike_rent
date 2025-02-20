package com.smart_bike_rent.exception;

public class ClientNotExistsException extends RuntimeException{
    public ClientNotExistsException(String message) {
        super(message);
    }
}
