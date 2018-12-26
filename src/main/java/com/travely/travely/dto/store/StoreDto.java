package com.travely.travely.dto.store;

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
    private Timestamp closeRime;
    //상가주소
    private String address;
    //상가 평점
    private double like;
    //상가id
    private long storeIdx;

    public StoreDto(String ownerName, String storeName, String storeCall, double latitude, double longitude, Timestamp openTime, Timestamp closeRime, String address, double like, long storeIdx) {
        this.ownerName = ownerName;
        this.storeName = storeName;
        this.storeCall = storeCall;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openTime = openTime;
        this.closeRime = closeRime;
        this.address = address;
        this.like = like;
        this.storeIdx = storeIdx;
    }
}
