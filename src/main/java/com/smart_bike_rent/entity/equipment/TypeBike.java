package com.smart_bike_rent.entity.equipment;

public enum TypeBike {
    SZOSOWY("Szosowy"),
    MTB("MTB"),
    CROSSOWY("Crossowy"),
    MIEJSKI("Miejski"),
    TREKINGOWY("Trekingowy");
    private final String nameType;

    TypeBike(String nameType) {
        this.nameType = nameType;
    }

    public String getNameType() {
        return nameType;
    }
}
