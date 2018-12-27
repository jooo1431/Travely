package com.travely.travely.dto.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreImgDto {
    private long storeIdx;
    private String storeName;
    private String storeImg;

    @Builder
    public StoreImgDto(long storeIdx, String storeName, String storeImg) {
        this.storeIdx = storeIdx;
        this.storeName = storeName;
        this.storeImg = storeImg;
    }
}
