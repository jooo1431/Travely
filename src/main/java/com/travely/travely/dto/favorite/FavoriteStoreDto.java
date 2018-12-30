package com.travely.travely.dto.favorite;

import com.travely.travely.domain.Store;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FavoriteStoreDto {

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

    private String regionName;

    public FavoriteStoreDto(Store store) {
        this.storeIdx = store.getStoreIdx();
        this.regionIdx = store.getRegionIdx();
        this.storeName = store.getStoreName();
        this.storeCall = store.getStoreCall();
        this.storeUrl = store.getStoreUrl();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.limit = store.getLimit();
    }
}
