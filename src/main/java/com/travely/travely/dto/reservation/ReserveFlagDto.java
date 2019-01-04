package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Reserve;
import lombok.Getter;

@Getter
public class ReserveFlagDto {
    private Boolean isReserve;

    public ReserveFlagDto(Reserve reserve) {
        if(reserve == null){
            this.isReserve = true;
        }else{
            this.isReserve = false;
        }
    }
}
