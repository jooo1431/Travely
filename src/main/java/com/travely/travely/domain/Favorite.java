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
    private int isFavorite;

    public Favorite(Long userIdx, Long storeIdx) {
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.isFavorite = 1;
    }
}
