package com.smart_bike_rent.exception;

public class EquipmentNotFoundException extends RuntimeException {

        public EquipmentNotFoundException(Long bikeId) {
            super("Nie znaleziono roweru o ID: " + bikeId);
    }
}
