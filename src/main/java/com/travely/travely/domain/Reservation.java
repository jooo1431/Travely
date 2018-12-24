package com.travely.travely.domain;

import com.travely.travely.dto.reservation.ReservationReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    public Reservation(final String reserveIdx, final ReservationReqDto reservationReqDto,final int state){
        this.reserveIdx = reserveIdx;
        this.userIdx = reservationReqDto.getUserIdx();
        this.storeIdx = reservationReqDto.getStoreIdx();
        this.startDate = reservationReqDto.getStartDate();
        this.startDay = reservationReqDto.getStartDay();
        this.startTime = reservationReqDto.getStartTime();
        this.endDate = reservationReqDto.getEndDate();
        this.endDay = reservationReqDto.getEndDay();
        this.endTime = reservationReqDto.getEndTime();
        this.state = state;
    }
}
