package com.travely.travely.dto.favorite;

import com.travely.travely.domain.Favorite;
import lombok.Getter;

@Getter
public class FavoriteResponseDto {
    private Long userIdx;
    private Long storeIdx;
    private Integer isFavorite;

    public FavoriteResponseDto(Favorite favorite) {
        this.userIdx = favorite.getUserIdx();
        this.storeIdx = favorite.getStoreIdx();
        this.isFavorite = favorite.getIsFavorite();
    }
}
