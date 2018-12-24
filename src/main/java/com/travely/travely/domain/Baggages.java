package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Baggages {
    private long bagIdx;
    private long bagCount;
    private long bagType;
    private String reserveIdx;

    @Builder
    public Baggages(long bagIdx, long bagCount, long bagType, String reserveIdx) {
        this.bagIdx = bagIdx;
        this.bagCount = bagCount;
        this.bagType = bagType;
        this.reserveIdx = reserveIdx;
    }
}
