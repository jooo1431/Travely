package com.travely.travely.util.typeHandler;

public enum ProgressType implements CommonEnum {
    ING(0, "payment in progress."),
    DONE(1, "payment completed"),
    CANCEL(2, "payment canceled.");

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
