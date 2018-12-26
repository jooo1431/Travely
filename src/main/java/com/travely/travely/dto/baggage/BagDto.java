package com.travely.travely.dto.baggage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class BagDto {
    @ApiModelProperty(example = "CARRIER", position = 1)
    private long bagType;
    @ApiModelProperty(example = "2", position = 2)
    private long bagcount;

    public BagDto(long bagType, long bagcount) {
        this.bagType = bagType;
        this.bagcount = bagcount;
    }
}
