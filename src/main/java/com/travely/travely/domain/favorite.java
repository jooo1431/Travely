package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class favorite {
    private long favoriteIdx;
    private long useIdx;
    private long storeIdx;

    @Builder
    public favorite(long favoriteIdx, long useIdx, long storeIdx) {
        this.favoriteIdx = favoriteIdx;
        this.useIdx = useIdx;
        this.storeIdx = storeIdx;
    }
}
