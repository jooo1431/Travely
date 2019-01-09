package com.travely.travely.dto.region;

import com.travely.travely.domain.Region;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class RegionResponseDto {

    private String regionName;
    private List<SimpleStoreResponseDto> simpleStoreResponseDtos;

    public RegionResponseDto(Region region) {
        this.regionName = region.getRegionName();
        this.simpleStoreResponseDtos = region.getStores().stream()
                .map(store -> new SimpleStoreResponseDto(store)).collect(Collectors.toList());
    }
}
