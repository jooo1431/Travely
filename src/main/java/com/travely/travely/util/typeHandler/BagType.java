package com.travely.travely.util.typeHandler;


import com.travely.travely.util.typeHandler.CommonEnum;

public enum BagType implements CommonEnum {
    Carrier(0, "캐리어"),
    etc(1, "기타");

    private int value;
    private String message;

    private BagType(int value, String message) {
        value = value;
        message = message;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "value = "+getValue()+", message = "+getMessage();
    }

}
