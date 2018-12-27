package com.travely.travely.dto.reservation;

import com.travely.travely.util.typeHandler.StateType;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ReserveJoinStore {
    private long storeIdx;
    private long reserveIdx;
    private long userIdx;
    private Timestamp startTime;
    private Timestamp endTime;
    private StateType state;
    private long price;
    private String reserveCode;
    private Timestamp depositTime;
    private Timestamp takeTime;
    private long ownerIdx;
    private String storeName;
    private long regionIdx;
    private String storeCall;
    private String storeUrl;
    private String address;
    private String openTime;
    private String closeTime;
    private double latitude;
    private double longitude;
    private long limit;

    @Builder
    public ReserveJoinStore(long storeIdx, long reserveIdx, long userIdx, Timestamp startTime, Timestamp endTime, StateType state, long price, String reserveCode, Timestamp depositTime, Timestamp takeTime, long ownerIdx, String storeName, long regionIdx, String storeCall, String storeUrl, String address, String openTime, String closeTime, double latitude, double longitude, long limit) {
        this.storeIdx = storeIdx;
        this.reserveIdx = reserveIdx;
        this.userIdx = userIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.price = price;
        this.reserveCode = reserveCode;
        this.depositTime = depositTime;
        this.takeTime = takeTime;
        this.ownerIdx = ownerIdx;
        this.storeName = storeName;
        this.regionIdx = regionIdx;
        this.storeCall = storeCall;
        this.storeUrl = storeUrl;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.limit = limit;
    }
}
