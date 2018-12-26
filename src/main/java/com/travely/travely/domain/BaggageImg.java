package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaggageImg {
    private long reserveIdx;
    private String bagImg;

    @Builder
    public BaggageImg(long reserveIdx, String bagImg) {
        this.reserveIdx = reserveIdx;
        this.bagImg = bagImg;
    }
}
