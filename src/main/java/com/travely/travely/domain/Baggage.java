package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Baggage {
    private long bagIdx;
    private long bagCount;
    private long bagType;
    private long reserveIdx;

    @Builder
    public Baggage(long bagIdx, long bagCount, long bagType, long reserveIdx) {
        this.bagIdx = bagIdx;
        this.bagCount = bagCount;
        this.bagType=bagType;
        this.reserveIdx = reserveIdx;
    }
}
