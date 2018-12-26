package com.travely.travely.domain;

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
    private long state;
    private long price;
    private long deleted;
    private String reserveCode;
    private Timestamp dipositTime;
    private Timestamp takeTime;

    @Builder
    public Reserve(long reserveIdx, long userIdx, long storeIdx, Timestamp startTime, Timestamp endTime, long state, long price, long deleted, String reserveCode, Timestamp dipositTime, Timestamp takeTime) {
        this.reserveIdx = reserveIdx;
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.price = price;
        this.deleted = deleted;
        this.reserveCode = reserveCode;
        this.dipositTime = dipositTime;
        this.takeTime = takeTime;
    }
}
