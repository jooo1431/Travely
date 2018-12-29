package com.travely.travely.dto.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegionResponseDto {

    private String regionName;
    private long cnt;
    private long regionIdx;


    @Builder
    public RegionResponseDto(String regionName, long cnt, long regionIdx) {
        this.regionName = regionName;
        this.cnt = cnt;
        this.regionIdx = regionIdx;
    }
}
