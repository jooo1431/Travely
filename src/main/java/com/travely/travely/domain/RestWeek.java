package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestWeek {
    private long weekIdx;
    private String week;
    private long storeIdx;

    @Builder
    public RestWeek(long weekIdx, String week, long storeIdx) {
        this.weekIdx = weekIdx;
        this.week = week;
        this.storeIdx = storeIdx;
    }
}
