package com.travely.travely.util.typeHandler;

public enum StateType implements CommonEnum {
    ReserveOk(0, "예약완료"),
    PayOk(1, "결제완료"),
    Archiving(2, "보관중"),
    TakeOff(3, "짐수거"),
    Cancel(4, "예약 및 결제 취소");

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

    public void checkReserve() {
        if (this.value < 3) throw new RuntimeException();
    }
}
