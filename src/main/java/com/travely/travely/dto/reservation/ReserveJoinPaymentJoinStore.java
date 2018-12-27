package com.travely.travely.dto.reservation;

import com.travely.travely.util.typeHandler.PayType;
import com.travely.travely.util.typeHandler.ProgressType;
import com.travely.travely.util.typeHandler.StateType;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ReserveJoinPaymentJoinStore {
    private long reserveIdx;
    private long userIdx;
    private long storeIdx;
    private Timestamp startTime;
    private Timestamp endTime;
    private StateType stateType;
    private long price;
    private String reserveCode;
    private Timestamp depositTime;
    private Timestamp takeTime;
    private long payIdx;
    private PayType payType;
    private ProgressType progressType;
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

    public ReserveJoinPaymentJoinStore(long reserveIdx, long userIdx, long storeIdx, Timestamp startTime, Timestamp endTime, StateType stateType, long price, String reserveCode, Timestamp depositTime, Timestamp takeTime, long payIdx, PayType payType, ProgressType progressType, long ownerIdx, String storeName, long regionIdx, String storeCall, String storeUrl, String address, String openTime, String closeTime, double latitude, double longitude, long limit) {
        this.reserveIdx = reserveIdx;
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stateType = stateType;
        this.price = price;
        this.reserveCode = reserveCode;
        this.depositTime = depositTime;
        this.takeTime = takeTime;
        this.payIdx = payIdx;
        this.payType = payType;
        this.progressType = progressType;
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
