package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreImg {
    private long storeIdx;
    private String storeImg;

    @Builder
    public StoreImg(long storeIdx, String storeImg) {
        this.storeIdx = storeIdx;
        this.storeImg = storeImg;
    }
}
