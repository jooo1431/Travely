package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {
    private long regionIdx;
    private String regionName;

    @Builder
    public Region(long regionIdx, String regionName) {
        this.regionIdx = regionIdx;
        this.regionName = regionName;
    }
}
