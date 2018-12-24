package com.travely.travely.dto.reservation;

import com.travely.travely.domain.Baggages;
import com.travely.travely.util.BagType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BagDto {
    @ApiModelProperty(example = "2", position = 1)
    private long bagCount;
    @ApiModelProperty(example = "캐리어", position = 2)
    private BagType bagType;

    @Builder
    public BagDto(Baggages baggages) {
        this.bagCount = baggages.getBagCount();
        this.bagType = baggages.getBagType();
    }
}
