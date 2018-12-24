package com.travely.travely.domain;

import com.travely.travely.dto.reservation.ReservationRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation {
    private String reserveIdx;
    private long userIdx;
    private long storeIdx;
    private String startDate;
    private String startDay;
    private String startTime;
    private String endDate;
    private String endDay;
    private String endTime;
    private long state;

    @Builder
    public Reservation(String reserveIdx, long userIdx, long storeIdx, String startDate, String startDay, String startTime, String endDate, String endDay, String endTime, long state) {
        this.reserveIdx = reserveIdx;
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startDate = startDate;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endDay = endDay;
        this.endTime = endTime;
        this.state = state;
    }

    @Builder
    public Reservation(final String reserveIdx, final ReservationRequest reservationRequest, final int state) {
        this.reserveIdx = reserveIdx;
        this.userIdx = reservationRequest.getUserIdx();
        this.storeIdx = reservationRequest.getStoreIdx();
        this.startDate = reservationRequest.getStartDate();
        this.startDay = reservationRequest.getStartDay();
        this.startTime = reservationRequest.getStartTime();
        this.endDate = reservationRequest.getEndDate();
        this.endDay = reservationRequest.getEndDay();
        this.endTime = reservationRequest.getEndTime();
        this.state = state;
    }
}
