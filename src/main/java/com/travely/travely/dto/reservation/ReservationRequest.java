package com.travely.travely.dto.reservation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReservationRequest {
    @ApiModelProperty(example = "1", position = 1)
    private long userIdx;

    @ApiModelProperty(example = "2", position = 2)
    private long storeIdx;

    @ApiModelProperty(position = 3)
    private Timestamp startTime;

    @ApiModelProperty(position = 4)
    private Timestamp endTime;

    @ApiModelProperty(position = 5)
    private List<BagDto> bagList;

    public ReservationRequest(final long userIdx, final long storeIdx, final Timestamp startTime, final Timestamp endTime, final List<BagDto> bagList) {
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bagList = bagList;
    }
}
