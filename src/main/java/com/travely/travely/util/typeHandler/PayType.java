package com.travely.travely.util.typeHandler;

public enum PayType implements CommonEnum {
    CASH(0, "현금"),
    CARD(1, "카드");

    private int value;
    private String message;

    private PayType(int value, String message) {
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
