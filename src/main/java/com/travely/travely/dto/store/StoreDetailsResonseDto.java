package com.travely.travely.dto.store;

import com.travely.travely.domain.Review;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.review.ReviewResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreDetailsResonseDto {

    private Long storeIdx;
    private Long ownerIdx;
    private String storeName;
    private String storeCall;
    private String storeUrl;
    private String address;
    private String openTime;
    private String closeTime;
    private Double latitude;
    private Double longitude;
    private Long limit;
    private Long currentBag;
    private Double grade;

    private List<ReviewResponseDto> reviewResponseDtos;
    private List<StoreImageResponseDto> storeImageResponseDtos;

    public StoreDetailsResonseDto(Store store, Long currentBag) {
        this.storeIdx = store.getStoreIdx();
        this.ownerIdx = store.getOwnerIdx();
        this.storeName = store.getStoreName();
        this.storeCall = store.getStoreCall();
        this.storeUrl = store.getStoreUrl();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime();
        this.closeTime = store.getCloseTime();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.limit = store.getLimit();
        this.currentBag = currentBag;
        this.reviewResponseDtos = store.getReviews().stream()
                .map(review -> new ReviewResponseDto(review)).collect(Collectors.toList());
        this.storeImageResponseDtos = store.getStoreImgs().stream()
                .map(storeImg -> new StoreImageResponseDto(storeImg)).collect(Collectors.toList());
        this.grade = store.getReviews().stream()
                .mapToDouble(Review::getLike).average().orElse(0);
    }
}

