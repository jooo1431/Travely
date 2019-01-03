package com.travely.travely.dto.review;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ReviewStoreResponseDto {
    private Long reviewIdx;
    private String content;
    private Long liked;
    private Long createAt;
    //store Info
    private long storeIdx;
    private long ownerIdx;
    private String storeName;
    private long regionIdx;
    private String storeCall;
    private String storeUrl;
    private String address;
    private Long openTime;
    private Long closeTime;
    private double latitude;
    private double longitude;
    private long limit;
    //storeImgUrl
    private String storeImgUrl;
}
