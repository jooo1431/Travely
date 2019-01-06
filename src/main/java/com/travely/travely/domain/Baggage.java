package com.travely.travely.domain;

import com.travely.travely.util.typeHandler.BagType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Baggage {
    private long bagIdx;
    private long bagCount;
    private BagType bagType;
    private long reserveIdx;

    @Builder
    public Baggage(long bagIdx, long bagCount, BagType bagType, long reserveIdx) {
        this.bagIdx = bagIdx;
        this.bagCount = bagCount;
        this.bagType = bagType;
        this.reserveIdx = reserveIdx;
    }
}
