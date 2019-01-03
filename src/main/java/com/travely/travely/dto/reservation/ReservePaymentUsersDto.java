package com.travely.travely.dto.reservation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservePaymentUsersDto {
    private long reserveIdx;
    private long userIdx;
    private long storeIdx;
    private Timestamp startTime;
    private Timestamp endTime;
    private long state;
    private long price;
    private String reserveCode;
    private Timestamp depositTime;
    private Timestamp takeTime;
    private long payIdx;
    private long payType;
    private long progressType;
    private String name;
    private String phone;
    private String profileImg;

    @Builder
    public ReservePaymentUsersDto(long reserveIdx, long userIdx, long storeIdx, Timestamp startTime, Timestamp endTime, long state, long price, String reserveCode, Timestamp depositTime, Timestamp takeTime, long payIdx, long payType, long progressType, String name, String phone, String profileImg) {
        this.reserveIdx = reserveIdx;
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.price = price;
        this.reserveCode = reserveCode;
        this.depositTime = depositTime;
        this.takeTime = takeTime;
        this.payIdx = payIdx;
        this.payType = payType;
        this.progressType = progressType;
        this.name = name;
        this.phone = phone;
        this.profileImg = profileImg;
    }
}
