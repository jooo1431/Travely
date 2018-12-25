package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Reservation;
import com.travely.travely.domain.Store;
import com.travely.travely.util.typeHandler.StateType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReservationResponse {

    private String reserveIdx;
    private long underUserIdx;
    private String startDate;
    private String startDay;
    private String startTime;
    private String endDate;
    private String endDay;
    private String endTime;
    private StateType stateType;

    private List<BagDto> bags;

    private Store store;


    @Builder
    public ReservationResponse(final Reservation reservation, final List<BagDto> bags, final Store store) {
        this.reserveIdx = reservation.getReserveIdx();
        this.underUserIdx = reservation.getUnderUserIdx();
        this.startDate = reservation.getStartDate();
        this.startDay = reservation.getStartDay();
        this.startTime = reservation.getStartTime();
        this.endDate = reservation.getEndDate();
        this.endDay = reservation.getEndDay();
        this.endTime = reservation.getEndTime();
        this.stateType = reservation.getStateType();

        this.bags = bags;

        this.store = store;
    }
}
