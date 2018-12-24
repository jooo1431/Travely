package com.travely.travely.util;

public class TypeUtil {

    public enum BagType {
        캐리어, //get.ordinal() = 0
        기타;     //get.ordinal() = 1
    }

    public enum ReservationState {
        예약완료,
        결제완료,
        보관중,
        짐수거;
    }
}
