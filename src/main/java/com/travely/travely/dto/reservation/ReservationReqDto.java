package com.travely.travely.dto.reservation;

import lombok.Getter;

import java.util.List;

@Getter
public class ReservationReqDto {
    private long userIdx;
    private long storeIdx;
    private String startDate;
    private String startDay;
    private String startTime;
    private String endDate;
    private String endDay;
    private String endTime;
    private List<BagDto> bagList;

    public ReservationReqDto(long userIdx, long storeIdx, String startDate, String startDay, String startTime, String endDate, String endDay, String endTime, List<BagDto> bagList) {
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startDate = startDate;
        this.startDay = startDay;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endDay = endDay;
        this.endTime = endTime;
        this.bagList = bagList;
    }
}
