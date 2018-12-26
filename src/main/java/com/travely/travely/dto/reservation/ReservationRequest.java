package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Baggage;
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
    private List<Baggage> baggages;

    @ApiModelProperty(example = "0", position = 6)
    private long payType;

    public ReservationRequest(long userIdx, long storeIdx, Timestamp startTime, Timestamp endTime, List<Baggage> baggages, long payType) {
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.baggages = baggages;
        this.payType = payType;
    }
}
