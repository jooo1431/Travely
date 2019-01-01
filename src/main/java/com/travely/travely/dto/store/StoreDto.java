package com.travely.travely.dto.store;

import com.travely.travely.domain.Store;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class StoreDto {
    //업주 - 상가 조인필요 , 리뷰

    //업주이름
    private String ownerName;
    //상가이름
    private String storeName;
    //상가전화번호
    private String storeCall;
    //상가위도
    private double latitude;
    //상가경도
    private double longitude;
    //상가영업시작시간
    private Timestamp openTime;
    //상가영업종료시간
    private Timestamp closeTime;
    //상가주소
    private String address;
    //상가 평점
    private double avgLike;
    //상가id
    private long storeIdx;

    @Builder
    public StoreDto(final Store store) {
        this.ownerName = store.getUsers().getName();
        this.storeName = store.getStoreName();
        this.storeCall = store.getStoreCall();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.address = store.getAddress();
        this.avgLike = store.getGrade();
        this.storeIdx = store.getStoreIdx();
    }
}
