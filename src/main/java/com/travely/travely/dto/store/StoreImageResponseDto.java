package com.travely.travely.dto.store;

import com.travely.travely.domain.StoreImg;
import lombok.Getter;

@Getter
public class StoreImageResponseDto {
    private Long storeImgIdx;
    private String storeImg;

    public StoreImageResponseDto(StoreImg storeImg) {
        this.storeImgIdx = storeImg.getStoreImgIdx();
        this.storeImg = storeImg.getStoreImgUrl();
    }
}
