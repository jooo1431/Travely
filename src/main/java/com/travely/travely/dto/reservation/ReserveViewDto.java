package com.travely.travely.dto.reservation;

import com.travely.travely.config.CommonConfig;
import com.travely.travely.domain.Baggage;
import com.travely.travely.domain.BaggageImg;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.dto.baggage.BagImgDto;
import com.travely.travely.dto.store.StoreDto;
import com.travely.travely.util.typeHandler.PayType;
import com.travely.travely.util.typeHandler.ProgressType;
import com.travely.travely.util.typeHandler.StateType;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

//예약상태 state
//예약코드 reserveCode
//맡길시간+찾을시간 start end time
//맡긴시간+찾은시간 deposit take time
//List 짐종류 개수 가격 List<bagggage> price
//총 이용시간 : 요금정보
//최종결제금액 결제타입 결제상태 payment.payType payment.progressType
//
////보관중인상태면
//짐 사진 List<baggageImg>
//
////가게정보
//위치정보(위도+경도)
//가게 idx
//가게이름
//가게영업시간
//가게영업중인지
//가게주소이름
@Getter
public class ReserveViewDto {
    private StateType stateType;
    private String reserveCode;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp depositTime;
    private Timestamp takeTime;
    private List<BagDto> bagDtos;
    private Long price;
    private PayType payType;
    private ProgressType progressType;

    private List<BagImgDto> bagImgDtos;

    private StoreDto storeDto;

    public List<BagDto> getBagDtos() {
        return CommonConfig.getCheckedList(bagDtos);
    }

    public List<BagImgDto> getBagImgDtos() {
        return CommonConfig.getCheckedList(bagImgDtos);
    }

    @Builder
    public ReserveViewDto(StateType stateType, String reserveCode, Timestamp startTime, Timestamp endTime, Timestamp depositTime, Timestamp takeTime, List<Baggage> baggages, Long price, PayType payType, ProgressType progressType, List<BaggageImg> baggageImgs, StoreDto storeDto) {
        this.stateType = stateType;
        this.reserveCode = reserveCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.depositTime = depositTime;
        this.takeTime = takeTime;
        this.bagDtos = baggages.stream()
                .map(baggage -> new BagDto(baggage)).collect(Collectors.toList());
        this.price = price;
        this.payType = payType;
        this.progressType = progressType;
        this.bagImgDtos = baggageImgs.stream()
                .map(baggageImg -> new BagImgDto(baggageImg)).collect(Collectors.toList());
        this.storeDto = storeDto;
    }
}
