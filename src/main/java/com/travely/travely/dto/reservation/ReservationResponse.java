package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Reservation;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.dto.store.StoreDto;
import com.travely.travely.util.typeHandler.StateType;
import lombok.Builder;
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
    //상태정보
    private StateType stateType;
    private long price;
    //예약코드
    private String reserveCode;
    //짐종류 갯수
    private List<BagDto> bagDtos;
    //상가정보 및 평점
    private StoreDto storeDto;

    public ReservationResponse(long userIdx, Timestamp startTime, Timestamp endTime, StateType stateType, long price, String reserveCode, List<BagDto> bagDtos, StoreDto storeDto) {
        this.userIdx = userIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stateType = stateType;
        this.price = price;
        this.reserveCode = reserveCode;
        this.bagDtos = bagDtos;
        this.storeDto = storeDto;
    }
}
