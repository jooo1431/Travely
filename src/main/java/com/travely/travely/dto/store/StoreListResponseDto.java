package com.travely.travely.dto.store;

import com.travely.travely.domain.StoreCount;
import com.travely.travely.domain.StoreJoinLocal;
import lombok.Getter;

@Getter
public class StoreListResponseDto {

    private long localName;
    private long cnt;
    private long localIdx;

    public StoreListResponseDto(final StoreCount storeCount) {

        this.cnt = storeCount.getCnt();
        this.localIdx = storeCount.getLocalIdx();
        this.localName = storeCount.getLocalName();
    }
}
