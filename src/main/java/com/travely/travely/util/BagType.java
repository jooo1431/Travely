package com.travely.travely.util;

public enum BagType {
    Carrier(0,"캐리어"),
    etc(1,"기타");

    private int value;
    private String message;

    private BagType(int value,String message){
        value=value;
        message=message;
    }
    public int getValue(){
        return value;
    }
    public String getMessage(){
        return message;
    }
}
