package com.travely.travely.dto.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StoreDetailsResponseDto {

    private List<StoreImgDto> storeImgs;
    private StoreDetailsInfoDto storeDetails;

    @Builder
    public StoreDetailsResponseDto(List<StoreImgDto> storeImgs, StoreDetailsInfoDto storeDetails) {
        this.storeImgs = storeImgs;
        this.storeDetails = storeDetails;
    }
}
