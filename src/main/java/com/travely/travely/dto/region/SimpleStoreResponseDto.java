package com.travely.travely.dto.region;

import com.travely.travely.domain.Store;
import lombok.Getter;

@Getter
public class SimpleStoreResponseDto {
    private Long storeIdx;
    private String storeName;
    private String address;
    private Long openTime;
    private Long closeTime;
    private double grade;
    private String storeImgUrl;


    public SimpleStoreResponseDto(Store store) {
        this.storeIdx = store.getStoreIdx();
        this.storeName = store.getStoreName();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime().getTime();
        this.closeTime = store.getCloseTime().getTime();
        this.grade = store.getGrade();
        if (store.getStoreImgs().size() != 0)
            this.storeImgUrl = store.getStoreImgs().get(0).getStoreImgUrl();
        else this.storeImgUrl="";

    }
}
