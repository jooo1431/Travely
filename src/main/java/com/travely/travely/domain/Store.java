package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    private long storeIdx;
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
    public Store(long storeIdx, long ownerIdx, String storeName, long regionIdx, String storeCall, String storeUrl, String address,
                 String openTime, String closeTime, double latitude, double longitude, long limit) {
        this.storeIdx = storeIdx;
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
