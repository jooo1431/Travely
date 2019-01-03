package com.travely.travely.dto.favorite;

import com.travely.travely.domain.Favorite;
import com.travely.travely.domain.Store;
import com.travely.travely.domain.StoreImg;
import com.travely.travely.dto.store.StoreImageResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FavoriteStoreResponseDto {

    private Long favoriteIdx;
    private Long storeIdx;
    private String storeName;
    private String address;
    private Timestamp openTime;
    private Timestamp closeTime;

    private Double grade;
    private String storeImageUrl;

    public FavoriteStoreResponseDto(Favorite favorite) {
        this.favoriteIdx = favorite.getFavoriteIdx();
        this.storeIdx = favorite.getStoreIdx();
        this.storeName = favorite.getStore().getStoreName();
        this.address = favorite.getStore().getAddress();
        this.openTime = favorite.getStore().getOpenTime();
        this.closeTime = favorite.getStore().getCloseTime();
        this.grade = favorite.getStore().getGrade();
        this.storeImageUrl = favorite.getStore().getStoreImgs().get(0).getStoreImg();
    }
}
