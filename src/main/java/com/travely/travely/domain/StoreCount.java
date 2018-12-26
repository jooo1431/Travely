package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreCount {

    private long localName;
    private long cnt;
    private long localIdx;


    @Builder
    public StoreCount(long localName, long cnt, long localIdx) {
        this.localName = localName;
        this.cnt = cnt;
        this.localIdx = localIdx;
    }
}
