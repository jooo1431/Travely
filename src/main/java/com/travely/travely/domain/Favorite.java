package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {
    private Long favoriteIdx;
    private Long userIdx;
    private Long storeIdx;
    private int isFavorite;

    private Store store;


    public Favorite(Long userIdx, Long storeIdx) {
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.isFavorite = 1;
    }
}
