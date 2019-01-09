package com.travely.travely.dto.store;

import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.review.ReviewResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreDetailsResonseDto {

    private Long storeIdx;
    private Long ownerIdx;
    private String storeName;
    private String storeCall;
    private String storeUrl;
    private String address;
    private Long openTime;
    private Long closeTime;
    private Double latitude;
    private Double longitude;
    private Long limit;
    private Long currentBag;
    private Double grade;
    private String addressNumber;
    private int available;
    private int isFavorite;

    private List<ReviewResponseDto> reviewResponseDtos;
    private List<StoreImageResponseDto> storeImageResponseDtos;
    private List<RestWeekResponseDto> restWeekResponseDtos;

    public StoreDetailsResonseDto(Store store) {
        this.storeIdx = store.getStoreIdx();
        this.ownerIdx = store.getOwnerIdx();
        this.storeName = store.getStoreName();
        this.storeCall = store.getStoreCall();
        this.storeUrl = store.getStoreUrl();
        this.address = store.getAddress();
        this.openTime = store.getOpenTime().getTime();
        this.closeTime = store.getCloseTime().getTime();
        this.latitude = store.getLatitude();
        this.longitude = store.getLongitude();
        this.limit = store.getLimit();
        this.currentBag = store.getReserves().stream().mapToLong(Reserve::getTotalBag).sum();
        this.addressNumber = store.getAddressNumber();
        this.available = store.getAvailable();
        this.reviewResponseDtos = store.getReviews().stream()
                .map(review -> new ReviewResponseDto(review)).collect(Collectors.toList());
        this.storeImageResponseDtos = store.getStoreImgs().stream()
                .map(storeImg -> new StoreImageResponseDto(storeImg)).collect(Collectors.toList());
        this.restWeekResponseDtos = store.getRestWeeks().stream()
                .map(restWeek -> new RestWeekResponseDto(restWeek)).collect(Collectors.toList());
        this.grade = Double.parseDouble(String.format("%.1f",store.getGrade()));
        this.isFavorite= store.getFavoriteState();
    }
}

