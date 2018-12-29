package com.travely.travely.service;


import com.travely.travely.dto.store.*;
import com.travely.travely.mapper.StoreMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreMapper storeMapper;


    public List<StoreListResponseDto> getStoreList(final long regionIdx) {
        return storeMapper.findStoreListDto(regionIdx);
    }

    public StoreDetailsInfoResonseDto getStoreDetails(final Long storeIdx) {
        return StoreDetailsInfoResonseDto.builder()
                .store(storeMapper.findStoreByStoreIdx(storeIdx))
                .storeImageResponseDtos(getStoreImage(storeIdx))
                .build();
    }

    public List<StoreImageResponseDto> getStoreImage(final Long storeIdx) {
        return storeMapper.findStoreImageByStoreIdx(storeIdx);
    }
}
