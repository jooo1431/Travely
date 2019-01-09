package com.travely.travely.dto.region;

import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.store.RestWeekResponseDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SimpleStoreResponseDto {
    private Long storeIdx;
    private String storeName;
    private String address;
    private Long openTime;
    private Long closeTime;
    private double grade;
    private String storeImgUrl;
    private Long limit;
    private Long currentBag;
    private int available;
    private List<RestWeekResponseDto> restWeekResponseDtos;


    public SimpleStoreResponseDto(Store store) {
        this.storeIdx = store.getStoreIdx();
        this.storeName = store.getStoreName();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime().getTime();
        this.closeTime = store.getCloseTime().getTime();
        this.grade = store.getGrade();
        if (store.getStoreImgs().size() != 0)
            this.storeImgUrl = store.getStoreImgs().get(0).getStoreImgUrl();
        else this.storeImgUrl = "";
        this.limit = store.getLimit();
        this.currentBag = store.getReserves().stream().mapToLong(Reserve::getTotalBag).sum();
        this.available = store.getAvailable();
        this.restWeekResponseDtos = store.getRestWeeks().stream()
                .map(restWeek -> new RestWeekResponseDto(restWeek)).collect(Collectors.toList());
    }
}
