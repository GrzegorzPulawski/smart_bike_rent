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

    // Lookup enum by display name
    public static SizeBike fromName(String name) {
        for (SizeBike size : SizeBike.values()) {
            if (size.nameSize.equalsIgnoreCase(name)) {
                return size;
            }
        }
        throw new IllegalArgumentException("Unknown size: " + name);
    }

}
