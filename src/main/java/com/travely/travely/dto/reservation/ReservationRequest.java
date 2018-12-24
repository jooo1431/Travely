package com.travely.travely.dto.reservation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReservationRequest {
    @ApiModelProperty(example = "1", position = 1)
    private long userIdx;

    @ApiModelProperty(example = "2", position = 2)
    private long storeIdx;

    @ApiModelProperty(example = "20181224", position = 3)
    private String startDate;

    @ApiModelProperty(example = "월요일", position = 4)
    private String startDay;

    @ApiModelProperty(example = "13:00", position = 5)
    private String startTime;

    @ApiModelProperty(example = "20181225", position = 6)
    private String endDate;

    @ApiModelProperty(example = "화요일", position = 7)
    private String endDay;

    @ApiModelProperty(example = "11:00", position = 8)
    private String endTime;

    @ApiModelProperty(position = 9)
    private List<BagDto> bagList;

    public ReservationRequest(long userIdx, long storeIdx, String startDate, String startDay, String startTime, String endDate, String endDay, String endTime, List<BagDto> bagList) {
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
