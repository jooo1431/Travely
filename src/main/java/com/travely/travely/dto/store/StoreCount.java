package com.travely.travely.dto.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCount {

    private String regionName;
    private long cnt;
    private long regionIdx;


    @Builder
    public StoreCount(String regionName, long cnt, long regionIdx) {
        this.regionName = regionName;
        this.cnt = cnt;
        this.regionIdx = regionIdx;
    }
}
