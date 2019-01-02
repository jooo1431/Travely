package com.travely.travely.util.typeHandler;

public enum BagType implements CommonEnum {
    CARRIER(0, "carrier"),
    ETC(1, "etc");

    private int value;
    private String message;

    private BagType(int value, String message) {
        this.value = value;
        this.message = message;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
