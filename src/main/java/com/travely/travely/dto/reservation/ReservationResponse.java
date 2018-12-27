package com.travely.travely.dto.reservation;

import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.dto.store.StoreDto;
import com.travely.travely.util.typeHandler.PayType;
import com.travely.travely.util.typeHandler.StateType;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Getter
public class ReservationResponse {

    //유저아이디
    private long userIdx;
    //맡고 찾는 시간
    private Timestamp startTime;
    private Timestamp endTime;
    //결제 종류
    private PayType payType;
    //상태정보
    private StateType stateType;
    private long price;
    //예약코드
    private String reserveCode;
    //짐종류 갯수
    private List<BagDto> bagDtos;
    //상가정보 및 평점
    private StoreDto storeDto;

    public ReservationResponse(final long userIdx,final ReservationRequest reservationRequest, final String reserveCode, final StoreDto storeDto, final long price, final StateType stateType) {
        this.userIdx = userIdx;
        this.startTime = reservationRequest.getStartTime();
        this.endTime = reservationRequest.getEndTime();
        this.payType=reservationRequest.getPayType();
        this.stateType = stateType;
        this.price = price;
        this.reserveCode = reserveCode;
        this.bagDtos = reservationRequest.getBagDtos();
        this.storeDto = storeDto;
    }
}
