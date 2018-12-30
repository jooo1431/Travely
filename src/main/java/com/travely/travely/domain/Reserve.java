package com.travely.travely.domain;

import com.travely.travely.util.typeHandler.StateType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

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
    private String reserveCode;
    private Timestamp depositTime;
    private Timestamp takeTime;

    private List<Baggage> baggages;
    private List<BaggageImg> baggageImgs;
    private Payment payment;

    @Builder
    public Reserve(long userIdx, long storeIdx, Timestamp startTime, Timestamp endTime, StateType state, long price, String reserveCode, Timestamp depositTime, Timestamp takeTime) {
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.price = price;
        this.reserveCode = reserveCode;
        this.depositTime = depositTime;
        this.takeTime = takeTime;
    }
}
