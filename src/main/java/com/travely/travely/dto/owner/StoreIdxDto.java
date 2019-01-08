package com.travely.travely.dto.owner;

import com.travely.travely.domain.Store;
import lombok.Getter;

@Getter
public class StoreIdxDto {
    private Long storeIdx;

    public StoreIdxDto(Store store) {
        this.storeIdx = store.getStoreIdx();
    }
}
