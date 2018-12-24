package com.travely.travely.util.typeHandler;

public enum StateType {
    예약완료(0),
    결제완료(1),
    보관중(2),
    짐수거(3);

    private int value;

    private StateType(int value) {
        value = value;
    }

    public int getValue(){
        return value;
    }

}
