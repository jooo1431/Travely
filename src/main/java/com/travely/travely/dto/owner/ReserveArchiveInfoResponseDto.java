package com.travely.travely.dto.owner;

import com.travely.travely.domain.Reserve;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.dto.baggage.BagImgDto;
import com.travely.travely.util.typeHandler.PayType;
import com.travely.travely.util.typeHandler.ProgressType;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReserveArchiveInfoResponseDto {
    private Long reserveIdx;
    private Long userIdx;
    private String userImgUrl;
    private String userName;
    private String userPhone;
    private PayType payType;
    private List<BagDto> bagDtoList;
    private Long startTime;
    private Long depositTime;
    private Long endTime;
    private Long takeTime;
    private ProgressType progressType;
    private Long price;
    private List<BagImgDto> bagImgDtos;

    public ReserveArchiveInfoResponseDto(final Reserve reserve) {
        this.reserveIdx = reserve.getReserveIdx();
        this.userIdx = reserve.getUserIdx();
        this.userImgUrl = reserve.getUsers().getProfileImg();
        this.userName = reserve.getUsers().getName();
        this.userPhone = reserve.getUsers().getPhone();
        this.payType = reserve.getPayment().getPayType();
        this.bagDtoList = reserve.getBaggages().stream().map(baggage -> new BagDto(baggage)).collect(Collectors.toList());
        this.startTime = reserve.getStartTime().getTime();
        this.depositTime = reserve.getDepositTime().getTime();
        this.endTime = reserve.getEndTime().getTime();
        this.takeTime = reserve.getTakeTime().getTime();
        this.progressType = reserve.getPayment().getProgressType();
        this.price = reserve.getPrice();
        this.bagImgDtos = reserve.getBaggageImgs().stream().map(baggageImg -> new BagImgDto(baggageImg)).collect(Collectors.toList());
    }
}