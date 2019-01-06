package com.travely.travely.service;

import com.travely.travely.domain.Store;
import com.travely.travely.dto.store.StoreDetailsResonseDto;
import com.travely.travely.exception.NotFoundStoreException;
import com.travely.travely.mapper.BaggageMapper;
import com.travely.travely.mapper.StoreMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreMapper storeMapper;

    private final BaggageMapper baggageMapper;

    @Transactional
    public StoreDetailsResonseDto getStoreDetails(final Long storeIdx) {
        Store store = storeMapper.findStoreByStoreIdx(storeIdx);
        if (store == null) throw new NotFoundStoreException();
        return new StoreDetailsResonseDto(store);
    }
}
