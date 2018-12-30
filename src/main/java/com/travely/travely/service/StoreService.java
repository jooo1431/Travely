package com.travely.travely.service;

import com.travely.travely.config.CommonConfig;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.store.StoreDetailsResonseDto;
import com.travely.travely.dto.store.StoreListResponseDto;
import com.travely.travely.mapper.BaggageMapper;
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

    private final BaggageMapper baggageMapper;

    public List<StoreListResponseDto> getStoreList(final long regionIdx) {
        return CommonConfig.getCheckedList(storeMapper.findStoreListDto(regionIdx));
    }

    public StoreDetailsResonseDto getStoreDetails(final Long storeIdx) {
        Store store = storeMapper.findStoreByStoreIdx(storeIdx);
        Long currentBag = baggageMapper.findSumCurrentBagByStoreIdx(storeIdx);
        if (store == null) throw new RuntimeException();
        return new StoreDetailsResonseDto(store,currentBag);
    }
}
