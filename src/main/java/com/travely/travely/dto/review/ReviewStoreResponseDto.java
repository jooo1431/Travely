package com.travely.travely.dto.review;

import com.travely.travely.domain.Store;
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

    public ReviewStoreResponseDto(Store store) {
        this.reviewIdx = store.getReviews().get(0).getReviewIdx();
        this.content = store.getReviews().get(0).getContent();
        this.liked = store.getReviews().get(0).getLiked();
        this.createAt = store.getReviews().get(0).getCreateAt().getTime();
        this.storeIdx = store.getStoreIdx();
        this.ownerIdx = store.getOwnerIdx();
        this.storeName = store.getStoreName();
        this.regionIdx = store.getRegionIdx();
        this.storeCall = store.getStoreCall();
        this.storeUrl = store.getStoreUrl();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime().getTime();
        this.closeTime = store.getCloseTime().getTime();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.limit = store.getLimit();
        if (store.getStoreImgs().size() == 0)
            this.storeImgUrl = "";
        else
            this.storeImgUrl = store.getStoreImgs().get(0).getStoreImgUrl();
    }
}
