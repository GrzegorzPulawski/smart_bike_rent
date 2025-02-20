package com.smart_bike_rent.exception;

public class EquipmentNotExists extends RuntimeException{
    public EquipmentNotExists(String message) {
        super(message);
    }
}
