package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Baggage;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.util.typeHandler.PayType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReservationRequest {

    @ApiModelProperty(example = "1", position = 2)
    private long storeIdx;

    @ApiModelProperty(example = "1545659383000", position = 3)
    private Timestamp startTime;

    @ApiModelProperty(example = "1545688183000", position = 4)
    private Timestamp endTime;

    @ApiModelProperty(position = 5)
    private List<BagDto> bagDtos;

    @ApiModelProperty(example = "CARD", position = 6)
    private PayType payType;

    public ReservationRequest(long storeIdx, Timestamp startTime, Timestamp endTime, List<BagDto> bagDtos, PayType payType) {
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bagDtos = bagDtos;
        this.payType = payType;
    }
}
