package com.travely.travely.dto.owner;

import com.travely.travely.domain.Store;
import lombok.Getter;

@Getter
public class OwnerInfoResponseDto {
    private Long storeIdx;
    private String ownerName;
    private String ownerImgUrl;
    private int serviceCount;
    private int reviewCount;
    private double storeGrade;
    private int onOff;
    private String storeName;
    private String address;
    private String addressNumber;
    private Long openTime;
    private Long closeTime;
    private String storeUrl;
    private String storeCall;

    public OwnerInfoResponseDto(Store store) {
        this.storeIdx = store.getStoreIdx();
        this.ownerName = store.getUsers().getName();
        this.ownerImgUrl = store.getUsers().getProfileImg();
        this.serviceCount = store.getReserves().size();
        this.reviewCount = store.getReviews().size();
        this.storeGrade = store.getGrade();
        this.onOff = store.getAvailable();
        this.storeName = store.getStoreName();
        this.address = store.getAddress();
        this.addressNumber = store.getAddressNumber();
        this.openTime = store.getOpenTime().getTime();
        this.closeTime = store.getCloseTime().getTime();
        this.storeUrl = store.getStoreUrl();
        this.storeCall = store.getStoreCall();
    }
}
