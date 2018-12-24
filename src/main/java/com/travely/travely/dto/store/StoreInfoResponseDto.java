package com.travely.travely.dto.store;

import com.travely.travely.domain.StoreJoinLocal;
import lombok.Getter;

@Getter
public class StoreInfoResponseDto {

    private long cnt;

    private String localName;

    public StoreInfoResponseDto(final StoreJoinLocal storeJoinLocal) {

        this.cnt = storeJoinLocal.getCnt();
        this.localName = storeJoinLocal.getLocalName();
    }
}
