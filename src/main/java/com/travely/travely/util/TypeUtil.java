package com.travely.travely.util;

public class TypeUtil {
    public enum  BagType {
        Carrier, //get.ordinal() = 0
        ETC;     //get.ordinal() = 1
    }
    public enum ReservationState{
        예약중,
        예약완료,
        보관중,
        짐수거;
    }
}
