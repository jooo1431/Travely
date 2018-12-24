package com.travely.travely.domain;

import com.travely.travely.util.typeHandler.BagType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Baggages {
    private long bagIdx;
    private long bagCount;
    private BagType bagType;
    private String reserveIdx;

    @Builder
    public Baggages(long bagIdx, long bagCount, BagType bagType, String reserveIdx) {
        this.bagIdx = bagIdx;
        this.bagCount = bagCount;
        this.bagType = bagType;
        this.reserveIdx = reserveIdx;
    }
}
