package com.travely.travely.dto.reservation;

import com.travely.travely.dto.baggage.BagDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReserveInfoDto {
    private ReservePaymentUsersDto reservePaymentUsersDto;
    private List<BagDto> bagDtoList;
    private List<String> bagImgs;

    @Builder
    public ReserveInfoDto(ReservePaymentUsersDto reservePaymentUsersDto, List<BagDto> bagDtoList, List<String> bagImgs) {
        this.reservePaymentUsersDto = reservePaymentUsersDto;
        this.bagDtoList = bagDtoList;
        this.bagImgs = bagImgs;
    }
}
