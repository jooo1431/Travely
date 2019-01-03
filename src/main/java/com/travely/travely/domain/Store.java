package com.travely.travely.domain;

import com.travely.travely.config.CommonConfig;
import com.travely.travely.dto.reservation.ReserveRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    private Long storeIdx;
    private Long ownerIdx;
    private String storeName;
    private Long regionIdx;
    private String storeCall;
    private String storeUrl;
    private String address;
    private Timestamp openTime;
    private Timestamp closeTime;
    private double latitude;
    private double longitude;
    private Long limit;

    private List<Review> reviews;
    private List<StoreImg> storeImgs;
    private List<RestWeek> restWeeks;
    private Users users;


    public List<Review> getReviews() {
        return CommonConfig.getCheckedList(reviews);
    }

    public List<StoreImg> getStoreImgs() {
        return CommonConfig.getCheckedList(storeImgs);
    }

    public List<RestWeek> getRestWeeks() {
        return CommonConfig.getCheckedList(restWeeks);
    }


    public Users getUsers() {
        if (this.users == null) throw new RuntimeException();
        return this.users;
    }

    public Double getGrade() {
        if (reviews == null) return 0D;
        return reviews.stream().mapToDouble(Review::getLike).average().orElse(0);
    }

    @Builder
    public Store(Long storeIdx, Long ownerIdx, String storeName, Long regionIdx, String storeCall, String storeUrl, String address, Timestamp openTime, Timestamp closeTime, Double latitude, Double longitude, Long limit) {
        this.storeIdx = storeIdx;
        this.ownerIdx = ownerIdx;
        this.storeName = storeName;
        this.regionIdx = regionIdx;
        this.storeCall = storeCall;
        this.storeUrl = storeUrl;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.limit = limit;
    }

    public Long getSpace(final Long totalBagCount) {
        final Long space = this.limit - totalBagCount;
        if (space > 0) return space;
        else throw new RuntimeException();
    }

    public void checkRestWeek(ReserveRequestDto reserveRequestDto) {
        final Timestamp startTime = new Timestamp(reserveRequestDto.getStartTime());
        final Timestamp endTime = new Timestamp(reserveRequestDto.getEndTime());
        for (RestWeek restWeek : this.getRestWeeks()) {
            restWeek.checkRest(startTime);
            restWeek.checkRest(endTime);
        }
    }

    public void checkReserveTime(ReserveRequestDto reserveRequestDto) {
        if (!checkHour(new Timestamp(reserveRequestDto.getStartTime()))) throw new RuntimeException();
        if (!checkHour(new Timestamp(reserveRequestDto.getEndTime()))) throw new RuntimeException();
    }

    private Boolean checkHour(Timestamp timestamp) {
        log.info("open : " + this.openTime.getHours());
        log.info("mytime : " + timestamp.getHours());
        log.info("close : " + this.closeTime.getHours());
        if (this.openTime.getHours() < timestamp.getHours() &&
                this.closeTime.getHours() > timestamp.getHours())
            return true;
        return false;
    }

}
