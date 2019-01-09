package com.travely.travely.dto.owner;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AllReserveResponseDto {

    private List<ReserveArchiveResponseDto> reserveResponseDtoList;    //예약목록
    private List<ReserveArchiveResponseDto> storeResponseDtoList;     //보관목록

    @Builder
    public AllReserveResponseDto(List<ReserveArchiveResponseDto> reserveResponseDtoList, List<ReserveArchiveResponseDto> storeResponseDtoList) {
        this.reserveResponseDtoList = reserveResponseDtoList;
        this.storeResponseDtoList = storeResponseDtoList;
    }
}
