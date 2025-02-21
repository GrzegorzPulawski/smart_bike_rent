package com.smart_bike_rent.entity.equipment;

public enum SizeBike {
    XS("Extra Small"),
    S("Small"),
    M("Medium"),
    L("Large"),
    XL("Extra Large");
    private final String nameSize;

    SizeBike(String nameSize) {
        this.nameSize = nameSize;
    }

    public String getName() {
        return nameSize;
    }
}
