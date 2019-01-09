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
import java.util.Calendar;
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
    private Favorite favorite;

    private List<Reserve> reserves;


    public Integer getFavoriteState() {
        if (this.favorite == null) return -1;
        return this.favorite.getIsFavorite();
    }

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

    public List<Reserve> getReserves() {
        return CommonConfig.getCheckedList(reserves);
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
        if (!checkHour(new Timestamp(reserveRequestDto.getStartTime())))
            throw new NotCorrectTimeException("예약시작시간이 잘못 입력되었습니다.\n" +
                    "storeIdx : " + reserveRequestDto.getStoreIdx() + "오픈 : " + getOpenTime() +
                    "\n입력된 예약시작 시간 : " + new Timestamp(reserveRequestDto.getStartTime()));
        if (!checkHour(new Timestamp(reserveRequestDto.getEndTime())))
            throw new NotCorrectTimeException("예약종료시간이 잘못 입력되었습니다.\n" +
                    "storeIdx : " + reserveRequestDto.getStoreIdx() + "클로즈시간 : " + getCloseTime() +
                    "\n입력된 예약종료 시간 : " + new Timestamp(reserveRequestDto.getEndTime()));
    }

    private Boolean checkHour(Timestamp timestamp) {

        final Long startMil = timestamp.getTime();
        Long openMil = this.openTime.getTime();
        Long closeMil = this.closeTime.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startMil);

        final int sYear = calendar.get(Calendar.YEAR);
        final int sMonth = calendar.get(Calendar.MONTH);
        final int sDay = calendar.get(Calendar.DATE);

        calendar.setTimeInMillis(openMil);
        int openDate = calendar.get(Calendar.DATE);
        int openHour = calendar.get(Calendar.HOUR_OF_DAY);
        int openMinute = calendar.get(Calendar.MINUTE);
        calendar.setTimeInMillis(closeMil);
        int closeDate = calendar.get(Calendar.DATE);
        int closeHour = calendar.get(Calendar.HOUR_OF_DAY);
        int closeMinute = calendar.get(Calendar.MINUTE);

        calendar.set(sYear, sMonth, sDay, openHour, openMinute);
        openMil = calendar.getTimeInMillis();
        calendar.set(sYear, sMonth, sDay, closeHour, closeMinute);
        closeMil = calendar.getTimeInMillis();

        if (openDate != closeDate) {
            closeMil = closeMil + 24 * 1000 * 60 * 60;
        }

        if ((openMil <= startMil && closeMil >= startMil))
            return true;
        else
            return false;
    }

    public void checkAvailable() {
        if (this.available == -1) throw new NotOpenStoreException();
    }
}
