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
    private long reserveIdx;

    @Builder
    public Baggage(long bagIdx, long bagCount, long reserveIdx) {
        this.bagIdx = bagIdx;
        this.bagCount = bagCount;
        this.reserveIdx = reserveIdx;
    }
}
