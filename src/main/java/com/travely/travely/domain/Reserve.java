package com.travely.travely.domain;

import com.travely.travely.util.typeHandler.PayType;
import com.travely.travely.util.typeHandler.StateType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reserve {
    private long reserveIdx;
    private long userIdx;
    private long storeIdx;
    private Timestamp startTime;
    private Timestamp endTime;
    private StateType state;
    private long price;
    private long deleted;
    private String reserveCode;
    private Timestamp depositTime;
    private Timestamp takeTime;
    private PayType payType;

    @Builder
    public Reserve(long userIdx, long storeIdx, Timestamp startTime, Timestamp endTime, StateType state, long price, long deleted, String reserveCode, Timestamp depositTime, Timestamp takeTime, PayType payType ) {
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.price = price;
        this.deleted = deleted;
        this.reserveCode = reserveCode;
        this.depositTime = depositTime;
        this.takeTime = takeTime;
        this.payType=payType;
    }
}
