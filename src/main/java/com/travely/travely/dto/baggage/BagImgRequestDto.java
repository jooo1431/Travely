package com.travely.travely.dto.baggage;

import com.travely.travely.config.CommonConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BagImgRequestDto {
    private List<String> bagImgUrl;

    public List<String> getBagImgUrl() {
        return CommonConfig.getCheckedList(this.bagImgUrl);
    }
}
