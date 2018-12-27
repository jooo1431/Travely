package com.travely.travely.dto.store;

import lombok.Getter;

@Getter
public class StoreImgDto {
    final long storeIdx;
    final String storeName;
    final String storeImg;

    public StoreImgDto(long storeIdx, String storeName, String storeImg) {
        this.storeIdx = storeIdx;
        this.storeName = storeName;
        this.storeImg = storeImg;
    }
}
