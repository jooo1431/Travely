package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Baggage;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.util.typeHandler.PayType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ReserveRequestDto {

    @ApiModelProperty(example = "1", position = 2)
    private long storeIdx;

    @ApiModelProperty(example = "1546227587000", position = 3)
    private Long startTime;

    @ApiModelProperty(example = "1546249187000", position = 4)
    private Long endTime;

    @ApiModelProperty(position = 5)
    private List<BagDto> bagDtos;

    @ApiModelProperty(example = "CARD", position = 6)
    private PayType payType;

    public ReserveRequestDto(long storeIdx, Long startTime, Long endTime, List<BagDto> bagDtos, PayType payType) {
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bagDtos = bagDtos;
        this.payType = payType;
    }

    public void checkCount() {
        bagDtos.forEach(bagDto -> {
            if (!bagDto.checkCount()) throw new RuntimeException();
        });
    }

    public Long getBagsCount() {
        return bagDtos.stream().mapToLong(BagDto::getBagCount).sum();
    }
}
