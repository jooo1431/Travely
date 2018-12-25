package com.travely.travely.domain;

import com.travely.travely.dto.reservation.ReservationRequest;
import com.travely.travely.util.typeHandler.StateType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation {
    private String reserveIdx;
    private long underUserIdx;
    private long storeIdx;
    private String startDate;
    private String startDay;
    private String startTime;
    private String endDate;
    private String endDay;
    private String endTime;
    private StateType stateType;

    @Builder
    public Reservation(String reserveIdx, long underUserIdx, long storeIdx, String startDate, String startDay, String startTime, String endDate, String endDay, String endTime, StateType stateType) {
        this.reserveIdx = reserveIdx;
        this.underUserIdx = underUserIdx;
        this.storeIdx = storeIdx;
        this.startDate = startDate;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endDay = endDay;
        this.endTime = endTime;
        this.stateType = stateType;
    }

    @Builder
    public Reservation(final String reserveIdx, final ReservationRequest reservationRequest, final StateType stateType) {
        this.reserveIdx = reserveIdx;
        this.underUserIdx = reservationRequest.getUserIdx();
        this.storeIdx = reservationRequest.getStoreIdx();
        this.startDate = reservationRequest.getStartDate();
        this.startDay = reservationRequest.getStartDay();
        this.startTime = reservationRequest.getStartTime();
        this.endDate = reservationRequest.getEndDate();
        this.endDay = reservationRequest.getEndDay();
        this.endTime = reservationRequest.getEndTime();
        this.stateType = stateType;
    }
}
