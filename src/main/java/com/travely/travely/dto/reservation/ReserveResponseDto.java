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
public class ReserveResponseDto {

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
    private StoreDto store;

    public ReserveResponseDto(final ReserveRequestDto reserveRequestDto, final String reserveCode, final StoreDto storeDto, final long price, final StateType stateType) {
        this.startTime = new Timestamp(reserveRequestDto.getStartTime());
        this.endTime = new Timestamp(reserveRequestDto.getEndTime());
        this.payType = reserveRequestDto.getPayType();
        this.stateType = stateType;
        this.price = price;
        this.reserveCode = reserveCode;
        this.bagDtos = reserveRequestDto.getBagDtos();
        this.store = storeDto;
    }

    public Reserve toEntity(final Long userIdx) {
        return Reserve.builder()
                .endTime(this.endTime)
                .price(this.price)
                .reserveCode(this.reserveCode)
                .startTime(this.startTime)
                .state(this.stateType)
                .storeIdx(store.getStoreIdx())
                .userIdx(userIdx)
                .depositTime(null)
                .takeTime(null)
                .build();
    }
}
