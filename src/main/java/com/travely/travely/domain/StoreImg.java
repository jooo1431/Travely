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
    private String storeImgUrl;

    @Builder
    public StoreImg(Long storeIdx, Long storeImgIdx, String storeImgUrl) {
        this.storeIdx = storeIdx;
        this.storeImgIdx = storeImgIdx;
        this.storeImgUrl = storeImgUrl;
    }
}
