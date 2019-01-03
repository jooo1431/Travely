package com.travely.travely.util.typeHandler;

public enum StateType implements CommonEnum {
    RESERVED(0, "reservation completed."),
    PAYED(1, "payment completed"),
    ARCHIVE(2, "archiving"),
    PICKUP(3, "pickup completed."),
    CANCEL(4, "reservation or payment canceled.");

    private int value;
    private String message;

    private StateType(int value, String message) {
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

    public Boolean checkReserve() {
        return this.value < 3;
    }
}
