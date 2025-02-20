package com.smart_bike_rent.exception;

public class EquipmentAlreadyExistsException extends RuntimeException{
    public EquipmentAlreadyExistsException(String message) {
        super(message);
    }
}
