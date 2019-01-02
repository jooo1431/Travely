package com.travely.travely.dto.review;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ReviewStoreResponseDto {
    private Long reviewIdx;
    private String content;
    private Long liked;
    private Timestamp createAt;
    //store Info
    private long storeIdx;
    private long ownerIdx;
    private String storeName;
    private long regionIdx;
    private String storeCall;
    private String storeUrl;
    private String address;
    private Timestamp openTime;
    private Timestamp closeTime;
    private double latitude;
    private double longitude;
    private long limit;
    //storeImgUrl
    private String storeImgUrl;
}
