package com.travely.travely.domain;

import com.travely.travely.util.typeHandler.StateType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReserveJoinStore {
    private long storeIdx;
    private String reserveIdx;
    private long underUserIdx;
    private String startDate;
    private String startDay;
    private String startTime;
    private String endDate;
    private String endDay;
    private String endTime;
    private StateType stateType;

    private long userIdx;
    private String storeName;
    private long localIdx;
    private String storeCall;
    private String storeUrl;
    private String duringTime;
    private String address;

    @Builder
    public ReserveJoinStore(long storeIdx, String reserveIdx, long underUserIdx, String startDate, String startDay, String startTime, String endDate, String endDay, String endTime, StateType stateType, long userIdx, String storeName, long localIdx, String storeCall, String storeUrl, String duringTime, String address) {
        this.storeIdx = storeIdx;
        this.reserveIdx = reserveIdx;
        this.underUserIdx = underUserIdx;
        this.startDate = startDate;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endDay = endDay;
        this.endTime = endTime;
        this.stateType = stateType;
        this.userIdx = userIdx;
        this.storeName = storeName;
        this.localIdx = localIdx;
        this.storeCall = storeCall;
        this.storeUrl = storeUrl;
        this.duringTime = duringTime;
        this.address = address;
    }
}
