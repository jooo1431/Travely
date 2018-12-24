package com.travely.travely.dto.store;

import com.travely.travely.domain.StoreJoinLocal;
import lombok.Getter;

@Getter
public class StoreInfoResponseDto {

    private long cnt;
    private String storeName;
    private long storeIdx;
    private String localName;

    public StoreInfoResponseDto(final StoreJoinLocal storeJoinLocal) {
        this.storeName=storeJoinLocal.getStoreName();
        this.cnt = storeJoinLocal.getCnt();
        this.storeIdx=storeJoinLocal.getStoreIdx();
        this.localName = storeJoinLocal.getLocalName();
    }
}
