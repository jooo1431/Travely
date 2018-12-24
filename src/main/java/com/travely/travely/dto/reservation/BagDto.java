package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Baggages;
import com.travely.travely.util.TypeUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BagDto {
    @ApiModelProperty(example = "2", position = 1)
    private long bagCount;
    @ApiModelProperty(example = "캐리어", position = 2)
    private String bagType;

    @Builder

    public BagDto(Baggages baggages) {
        this.bagCount = baggages.getBagCount();
        if(baggages.getBagType()==0) this.bagType = "캐리어";
        else if(baggages.getBagType()==1) this.bagType="기타";
    }
}
