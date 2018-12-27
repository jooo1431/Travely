package com.travely.travely.util.typeHandler;

public enum ProgressType implements CommonEnum {
    ING(0, "결제진행중"),
    DONE(1, "결제완료"),
    CANCEL(2,"결제취소");

    private int value;
    private String message;

    private ProgressType(int value, String message) {
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
