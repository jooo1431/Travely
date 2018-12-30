package com.travely.travely.domain;

import com.travely.travely.config.CommonConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    private long storeIdx;
    private long ownerIdx;
    private String storeName;
    private long regionIdx;
    private String storeCall;
    private String storeUrl;
    private String address;
    private String openTime;
    private String closeTime;
    private double latitude;
    private double longitude;
    private long limit;

    private List<Review> reviews;
    private List<StoreImg> storeImgs;
    private List<RestWeek> restWeeks;

    public List<Review> getReviews() {
        return CommonConfig.getCheckedList(reviews);
    }

    public List<StoreImg> getStoreImgs() {
        return CommonConfig.getCheckedList(storeImgs);
    }

    public List<RestWeek> getRestWeeks() {
        return CommonConfig.getCheckedList(restWeeks);
    }
}
