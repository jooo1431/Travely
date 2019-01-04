package com.travely.travely.dto.store;

import com.travely.travely.domain.Store;
import com.travely.travely.domain.StoreImg;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@Slf4j
@Getter
public class StoreInfoResponseDto {
    private Long storeIdx;
    private Long ownerIdx;
    private String storeName;
    private String storeCall;
    private String storeUrl;
    private String address;
    private Long openTime;
    private Long closeTime;
    private Double latitude;
    private Double longitude;
    private Long limit;
    private String storeImage;

    public StoreInfoResponseDto(Store store) {
        this.storeIdx = store.getStoreIdx();
        this.ownerIdx = store.getOwnerIdx();
        this.storeName = store.getStoreName();
        this.storeCall = store.getStoreCall();
        this.storeUrl = store.getStoreUrl();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime().getTime();
        this.closeTime = store.getCloseTime().getTime();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.limit = store.getLimit();
        StoreImg storeImg = store.getStoreImgs().stream().findFirst().orElse(null);
        if (storeImg == null) {
            this.storeImage = "";
        } else {
            this.storeImage = storeImg.getStoreImgUrl();
        }

    }
}
