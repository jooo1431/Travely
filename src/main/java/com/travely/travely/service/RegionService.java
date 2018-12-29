package com.travely.travely.service;

import com.travely.travely.dto.store.RegionResponseDto;
import com.travely.travely.mapper.RegionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionMapper regionMapper;

    public List<RegionResponseDto> getStoreCount() {
        return regionMapper.findRegionDto();
    }

}
