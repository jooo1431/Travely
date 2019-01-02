package com.travely.travely.dto.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreListResponseDto {

    private String storeName;
    private long storeIdx;
    private String regionName;
    private long regionIdx;

    @Builder
    public StoreListResponseDto(String storeName, long storeIdx, String regionName, long regionIdx) {
        this.storeName = storeName;
        this.storeIdx = storeIdx;
        this.regionName = regionName;
        this.regionIdx = regionIdx;
    }
}
