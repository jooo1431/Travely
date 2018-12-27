package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Reserve;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.dto.store.StoreDto;
import com.travely.travely.util.typeHandler.PayType;
import com.travely.travely.util.typeHandler.StateType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReservationResponse {

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
    //짐 사진
    private List<String> bagImgs;
    //상가정보 및 평점
    private StoreDto storeDto;


    public ReservationResponse(final ReservationRequest reservationRequest, final String reserveCode, final StoreDto storeDto, final long price, final StateType stateType) {
        this.startTime = reservationRequest.getStartTime();
        this.endTime = reservationRequest.getEndTime();
        this.payType = reservationRequest.getPayType();
        this.stateType = stateType;
        this.price = price;
        this.reserveCode = reserveCode;
        this.bagDtos = reservationRequest.getBagDtos();
        this.storeDto = storeDto;
        this.bagImgs=null;
    }
    public ReservationResponse(final Reserve reserve,final List<BagDto> bagDtos, final String reserveCode, final StoreDto storeDto, final long price, final StateType stateType, final List<String> bagImgs) {
        this.startTime = reserve.getStartTime();
        this.endTime = reserve.getEndTime();
        this.payType = reserve.getPayType();
        this.stateType = stateType;
        this.price = price;
        this.reserveCode = reserveCode;
        this.bagDtos = bagDtos;
        this.storeDto = storeDto;
        this.bagImgs=bagImgs;
    }
}
