package com.travely.travely.dto.store;

import com.travely.travely.domain.StoreJoinLocal;
import lombok.Getter;
import org.apache.catalina.Store;

@Getter
public class StoreDetailsResponseDto {

    private long storeIdx;
    private String storeName;
    private String storeCall;
    private String storeUrl;
    private String duringTime;
    private String address;
    private String localName;


    public StoreDetailsResponseDto(final StoreJoinLocal storeJoinLocal) {

        this.storeIdx = storeJoinLocal.getStoreIdx();
        this.storeName = storeJoinLocal.getStoreName();
        this.storeCall =storeJoinLocal.getStoreCall();
        this.storeUrl = storeJoinLocal.getStoreUrl();
        this.duringTime = storeJoinLocal.getDuringTime();
        this.address = storeJoinLocal.getAddress();
        this.localName = storeJoinLocal.getLocalName();
    }
}
