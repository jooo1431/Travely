package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {
    private long favoriteIdx;
    private long userIdx;
    private long storeIdx;

    @Builder
    public Favorite(long favoriteIdx, long userIdx, long storeIdx) {
        this.favoriteIdx = favoriteIdx;
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
    }
}
