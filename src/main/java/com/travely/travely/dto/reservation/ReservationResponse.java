package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Baggages;
import com.travely.travely.domain.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReservationResponse {

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
    private List<BagDto> bags;

    @Builder
    public ReservationResponse(final Reservation reservation, final List<BagDto> bags) {
        this.reserveIdx = reservation.getReserveIdx();
        this.userIdx = reservation.getUserIdx();
        this.storeIdx = reservation.getStoreIdx();
        this.startDate = reservation.getStartDate();
        this.startDay = reservation.getStartDay();
        this.startTime = reservation.getStartTime();
        this.endDate = reservation.getEndDate();
        this.endDay = reservation.getEndDay();
        this.endTime = reservation.getEndTime();
        this.state = reservation.getState();
        this.bags = bags;
    }
}
