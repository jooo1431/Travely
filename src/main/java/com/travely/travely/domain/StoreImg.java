package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreImg {
    private Long storeIdx;
    private Long storeImgIdx;
    private String storeImg;

    @Builder
    public StoreImg(Long storeIdx, Long storeImgIdx, String storeImg) {
        this.storeIdx = storeIdx;
        this.storeImgIdx = storeImgIdx;
        this.storeImg = storeImg;
    }
}
