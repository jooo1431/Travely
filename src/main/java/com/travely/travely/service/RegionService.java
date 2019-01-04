package com.travely.travely.service;

import com.travely.travely.dto.region.RegionResponseDto;
import com.travely.travely.mapper.RegionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionMapper regionMapper;

    public List<RegionResponseDto> getAllRegion() {
        return regionMapper.findAllRegion()
                .stream().map(region -> new RegionResponseDto(region)).collect(Collectors.toList());
    }

    public List<RegionResponseDto> getAllRegion(Long userIdx) {
        return regionMapper.findAllRegionByUserIdx(userIdx)
                .stream().map(region -> new RegionResponseDto(region))
                .filter(regionResponseDto -> !regionResponseDto.getSimpleStoreResponseDtos().isEmpty())
                .collect(Collectors.toList());
    }
}
