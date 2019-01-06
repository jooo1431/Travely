package com.travely.travely.dto.baggage;

import com.travely.travely.domain.Baggage;
import com.travely.travely.util.typeHandler.BagType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BagDto {
    @ApiModelProperty(example = "CARRIER", position = 1)
    private BagType bagType;
    @ApiModelProperty(example = "2", position = 2)
    private long bagCount;

    public BagDto(BagType bagType, long bagCount) {
        this.bagType = bagType;
        this.bagCount = bagCount;
    }

    public BagDto(Baggage baggage) {
        this.bagType = baggage.getBagType();
        this.bagCount = baggage.getBagCount();
    }

    public Boolean checkCount() {
        return this.bagCount > 0;
    }
}
