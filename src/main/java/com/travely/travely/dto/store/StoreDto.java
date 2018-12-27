package com.travely.travely.dto.store;

import lombok.Getter;

import java.util.List;

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
    private String openTime;
    //상가영업종료시간
    private String closeTime;
    //상가주소
    private String address;
    //상가 평점
    private double avgLike;
    //상가id
    private long storeIdx;

    public StoreDto(StoreJoinUsersDto storeJoinUsersDto, final double avgLike) {
        this.ownerName = storeJoinUsersDto.getName();
        this.storeName = storeJoinUsersDto.getStoreName();
        this.storeCall = storeJoinUsersDto.getStoreCall();
        this.latitude = storeJoinUsersDto.getLatitude();
        this.longitude = storeJoinUsersDto.getLongitude();
        this.openTime = storeJoinUsersDto.getOpenTime();
        this.closeTime = storeJoinUsersDto.getCloseTime();
        this.address = storeJoinUsersDto.getAddress();
        this.avgLike = avgLike;
        this.storeIdx = storeJoinUsersDto.getStoreIdx();
    }
}
