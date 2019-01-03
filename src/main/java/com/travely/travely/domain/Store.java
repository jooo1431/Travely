package com.travely.travely.domain;

import com.travely.travely.config.CommonConfig;
import com.travely.travely.dto.reservation.ReserveRequestDto;
import com.travely.travely.exception.ExceedCapacityException;
import com.travely.travely.exception.NotCorrectTimeException;
import com.travely.travely.exception.NotOpenStoreException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
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
    private String addressNumber;
    private Timestamp openTime;
    private Timestamp closeTime;
    private double latitude;
    private double longitude;
    private long limit;
    private int available;

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
        return getReviews().stream().mapToDouble(Review::getLiked).average().orElse(0);
    }

    public Long getSpace(final Long totalBagCount) {
        final Long space = this.limit - totalBagCount;
        if (space > 0) return space;
        else throw new ExceedCapacityException();
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
        if (!checkHour(new Timestamp(reserveRequestDto.getStartTime()))) throw new NotCorrectTimeException();
        if (!checkHour(new Timestamp(reserveRequestDto.getEndTime()))) throw new NotCorrectTimeException();
    }

    private Boolean checkHour(Timestamp timestamp) {
        if (this.openTime.getHours() < timestamp.getHours() &&
                this.closeTime.getHours() > timestamp.getHours())
            return true;
        return false;
    }

    public void checkAvailable() {
        if (this.available == 0) throw new NotOpenStoreException();
    }
}
