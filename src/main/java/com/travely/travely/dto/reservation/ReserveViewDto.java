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

@Getter
public class ReserveViewDto {
    private StateType stateType;
    private String reserveCode;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp depositTime;
    private Timestamp takeTime;
    private List<BagDto> bagDtos;
    private Long priceIdx;
    private Long priceUnit;
    private Long price;
    private PayType payType;
    private ProgressType progressType;

    private List<BagImgDto> bagImgDtos;

    private StoreDto store;

    public List<BagDto> getBagDtos() {
        return CommonConfig.getCheckedList(bagDtos);
    }

    public List<BagImgDto> getBagImgDtos() {
        return CommonConfig.getCheckedList(bagImgDtos);
    }

    @Builder
    public ReserveViewDto(StateType stateType, String reserveCode, Timestamp startTime, Timestamp endTime, Timestamp depositTime, Timestamp takeTime, List<Baggage> baggages, Long price, PayType payType, ProgressType progressType, List<BaggageImg> baggageImgs, StoreDto storeDto, Long priceIdx, Long priceUnit) {
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
        this.store = storeDto;
        this.priceIdx = priceIdx;
        this.priceUnit = priceUnit;
    }
}
