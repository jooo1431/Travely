package com.travely.travely.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Store {
    private long storeIdx;
    private long userIdx;
    private String storeName;
    private long localIdx;
    private String storeCall;
    private String storeUrl;
    private String duringTime;
    private String address;

    @Builder
    public Store(long storeIdx, long userIdx, String storeName, long localIdx, String storeCall, String storeUrl, String duringTime, String address) {
        this.storeIdx = storeIdx;
        this.userIdx = userIdx;
        this.storeName = storeName;
        this.localIdx = localIdx;
        this.storeCall = storeCall;
        this.storeUrl = storeUrl;
        this.duringTime = duringTime;
        this.address = address;
    }
}
