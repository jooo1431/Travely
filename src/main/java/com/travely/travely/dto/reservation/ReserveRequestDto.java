package com.travely.travely.dto.reservation;

import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.exception.ExceedCapacityException;
import com.travely.travely.exception.NotCorrectTimeException;
import com.travely.travely.exception.NotFoundBaggageException;
import com.travely.travely.util.typeHandler.PayType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Slf4j
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
        if (this.startTime > this.endTime){
            throw new NotCorrectTimeException("예약 시작시간 보다 예약 종료시간이 뒤에 있어야합니다.");
        }
    }

    public void checkCurrentTime() {
        final Long currentTime = new Timestamp(System.currentTimeMillis()).getTime()-1000*60*3;
        if (this.startTime - currentTime < 0) {
            throw new NotCorrectTimeException("사용자가 입력한 시작시간 : "+new Timestamp(this.startTime)+"입력시 시간 - 3분 : "+new Timestamp(currentTime));
        }
    }

    public Long gainBagsCount() {
        return bagDtos.stream().mapToLong(BagDto::getBagCount).sum();
    }
}
