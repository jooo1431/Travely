package com.travely.travely.dto.store;

import com.travely.travely.domain.Store;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreDetailsInfoResonseDto {

    private Long storeIdx;
    private Long ownerIdx;
    private String storeName;
    private String storeCall;
    private String storeUrl;
    private String address;
    private String openTime;
    private String closeTime;
    private Double latitude;
    private Double longitude;
    private Long limit;
    private List<StoreImageResponseDto> storeImageResponseDtos;

    @Builder
    public StoreDetailsInfoResonseDto(Store store, List<StoreImageResponseDto> storeImageResponseDtos) {
        this.storeIdx = store.getStoreIdx();
        this.ownerIdx = store.getOwnerIdx();
        this.storeName = store.getStoreName();
        this.storeCall = store.getStoreCall();
        this.storeUrl = store.getStoreUrl();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.latitude = store.getLongitude();
        this.longitude = store.getLatitude();
        this.limit = store.getLimit();
        this.storeImageResponseDtos = storeImageResponseDtos;
    }
}

