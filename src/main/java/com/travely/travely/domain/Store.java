package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    private Long storeIdx;
    private Long ownerIdx;
    private String storeName;
    private Long regionIdx;
    private String storeCall;
    private String storeUrl;
    private String address;
    private String openTime;
    private String closeTime;
    private Double latitude;
    private Double longitude;
    private Long limit;

    @Builder
    public Store(Long storeIdx, Long ownerIdx, String storeName, Long regionIdx, String storeCall, String storeUrl, String address, String openTime, String closeTime, Double latitude, Double longitude, Long limit) {
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
