package com.travely.travely.dto.region;

import com.travely.travely.domain.Store;

public class SimpleStoreResponseDto {
    public Long storeIdx;
    public String storeName;

    public SimpleStoreResponseDto(Store store) {
        this.storeIdx = store.getStoreIdx();
        this.storeName = store.getStoreName();
    }
}
