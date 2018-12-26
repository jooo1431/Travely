package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Reservation;
import com.travely.travely.domain.Store;
import com.travely.travely.util.typeHandler.StateType;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class ReservationResponse {

    private String reserveIdx;
    private long underUserIdx;
    private Timestamp startTime;
    private Timestamp endTime;
    private StateType stateType;

    private List<BagDto> bags;

    private long price;

    private Store store;


    @Builder
    public ReservationResponse(final Reservation reservation, final List<BagDto> bags, final Store store, final long price) {
        this.reserveIdx = reservation.getReserveIdx();
        this.underUserIdx = reservation.getUnderUserIdx();
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();
        this.stateType = reservation.getStateType();

        this.bags = bags;

        this.price = price;

        this.store = store;
    }
}
