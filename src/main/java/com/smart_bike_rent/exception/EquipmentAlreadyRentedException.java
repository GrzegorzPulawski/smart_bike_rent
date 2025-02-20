package com.smart_bike_rent.exception;

public class EquipmentAlreadyRentedException extends RuntimeException{
    public EquipmentAlreadyRentedException(String message) {
        super(message);
    }
}
