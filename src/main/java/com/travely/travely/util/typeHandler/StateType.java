package com.travely.travely.util.typeHandler;

public enum StateType implements CommonEnum {
    ReserveOk(0, "예약완료"),
    PayOk(1, "결재완료"),
    Archiving(2, "보관중"),
    TakeOff(3, "짐수거");

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
}
