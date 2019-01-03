package com.travely.travely.dto.reservation;

import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.exception.ExceedCapacityException;
import com.travely.travely.exception.NotCorrectTimeException;
import com.travely.travely.exception.NotFoundBaggageException;
import com.travely.travely.util.typeHandler.PayType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

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

    public List<BagDto> getBagDtos() {
        if (this.bagDtos == null) throw new NotFoundBaggageException();
        return bagDtos;
    }

    public void checkSpace(final Long limit) {
        if (!(limit - gainBagsCount() >= 0)) throw new ExceedCapacityException();
    }

    public void checkCount() {
        getBagDtos().forEach(bagDto -> {
            if (!bagDto.checkCount()) throw new NotFoundBaggageException();
        });
    }

    public void checkTime() {
        if (this.startTime > this.endTime) throw new NotCorrectTimeException();
    }

    public void checkCurrentTime(){
        final Long currentTime = new Timestamp(System.currentTimeMillis()).getTime();
        if(this.startTime-currentTime<0) throw new NotCorrectTimeException();
    }

    public Long gainBagsCount() {
        return bagDtos.stream().mapToLong(BagDto::getBagCount).sum();
    }
}
