package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {
    private long favoriteIdx;
    private long useIdx;
    private long storeIdx;
    private int isFavorite;

    @Builder
    public Favorite(long favoriteIdx, long useIdx, long storeIdx,int isFavorite) {
        this.favoriteIdx = favoriteIdx;
        this.useIdx = useIdx;
        this.storeIdx = storeIdx;
        this.isFavorite = isFavorite;
    }
}
