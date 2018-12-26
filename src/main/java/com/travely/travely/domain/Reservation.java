package com.travely.travely.domain;

import com.travely.travely.dto.reservation.ReservationRequest;
import com.travely.travely.util.typeHandler.StateType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation {
    private String reserveIdx;
    private long underUserIdx;
    private long storeIdx;
    private Timestamp startTime;
    private Timestamp endTime;
    private StateType stateType;

    @Builder
    public Reservation(String reserveIdx, long underUserIdx, long storeIdx, Timestamp startTime, Timestamp endTime, StateType stateType) {
        this.reserveIdx = reserveIdx;
        this.underUserIdx = underUserIdx;
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stateType = stateType;
    }

    @Builder
    public Reservation(final String reserveIdx, final ReservationRequest reservationRequest, final StateType stateType) {
        this.reserveIdx = reserveIdx;
        this.underUserIdx = reservationRequest.getUserIdx();
        this.storeIdx = reservationRequest.getStoreIdx();
        this.startTime = reservationRequest.getStartTime();
        this.endTime = reservationRequest.getEndTime();
        this.stateType = stateType;
    }
}
