package com.travely.travely.dto.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreJoinUsersDto {
    private String name;
    private String storeName;
    private String address;
    private String storeCall;
    private double latitude;
    private double longitude;
    private String openTime;
    private String closeTime;
    private long storeIdx;

    @Builder
    public StoreJoinUsersDto(String name, String storeName, String address, String storeCall, double latitude, double longitude, String openTime, String closeTime, long storeIdx) {
        this.name = name;
        this.storeName = storeName;
        this.address = address;
        this.storeCall = storeCall;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.storeIdx = storeIdx;
    }
}
