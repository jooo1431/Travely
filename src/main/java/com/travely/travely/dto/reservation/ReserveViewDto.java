package com.travely.travely.dto.reservation;

import com.travely.travely.config.CommonConfig;
import com.travely.travely.domain.Reserve;
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
    private Long startTime;
    private Long endTime;
    private Long depositTime;
    private Long takeTime;
    private List<BagDto> bagDtos;
    private Long priceIdx;
    private Long priceUnit;
    private Long extraChargeCount;
    private Long extraCharge;
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
    public ReserveViewDto(final Reserve reserve, final StoreDto storeDto, final Long priceIdx, final Long priceUnit, final Long extraCharge, final Long extraChargeCount) {
        this.stateType = reserve.getState();
        this.reserveCode = reserve.getReserveCode();
        this.startTime = reserve.getStartTime().getTime();
        this.endTime = reserve.getEndTime().getTime();
        this.depositTime = reserve.getDepositTime().getTime();
        this.takeTime = reserve.getTakeTime().getTime();
        this.bagDtos = reserve.getBaggages().stream()
                .map(baggage -> new BagDto(baggage)).collect(Collectors.toList());
        this.price = reserve.getPrice();
        this.payType = reserve.getPayment().getPayType();
        this.progressType = reserve.getPayment().getProgressType();
        this.bagImgDtos = reserve.getBaggageImgs().stream()
                .map(baggageImg -> new BagImgDto(baggageImg)).collect(Collectors.toList());
        this.store = storeDto;
        this.priceIdx = priceIdx;
        this.priceUnit = priceUnit;
        this.extraCharge = extraCharge;
        this.extraChargeCount = extraChargeCount;
    }
}
