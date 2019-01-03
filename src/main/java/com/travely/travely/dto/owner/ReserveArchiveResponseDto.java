package com.travely.travely.dto.owner;

import com.travely.travely.domain.Reserve;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.util.typeHandler.ProgressType;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReserveArchiveResponseDto {
    private Long reserveIdx;
    private Long userIdx;
    private String userImg;
    private String userName;
    private ProgressType progressType;
    private Long price;
    private List<BagDto> bagDtos;
    private Timestamp startTime;
    private Timestamp endTime;

    @Builder
    public ReserveArchiveResponseDto(Reserve reserve) {
        this.reserveIdx = reserve.getReserveIdx();
        this.userIdx = reserve.getUserIdx();
        this.userImg = reserve.getUsers().getProfileImg();
        this.userName = reserve.getUsers().getName();
        this.progressType = reserve.getPayment().getProgressType();
        this.price = reserve.getPrice();
        this.bagDtos = reserve.getBaggages().stream().map(baggage -> new BagDto(baggage)).collect(Collectors.toList());
        this.startTime = reserve.getStartTime();
        this.endTime = reserve.getEndTime();
    }
}
