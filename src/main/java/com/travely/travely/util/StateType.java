package com.travely.travely.util;

public enum StateType {
    예약완료(0),
    결제완료(1),
    보관중(2),
    짐수거(3);

    private int value;
    private String message;

    private StateType(int value){
        value=value;
    }
    public int getValue(){
        return value;
    }

    private StateType(String message){
        message=message;
    }
    public String getMessage(){
        return message;
    }
}
