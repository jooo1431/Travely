package com.travely.travely.service;


import com.travely.travely.domain.StoreJoinLocal;
import com.travely.travely.dto.store.StoreInfoResponseDto;
import com.travely.travely.mapper.StoreMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreMapper storeMapper;

    public List<StoreInfoResponseDto> getStoreInfo() {

        final List<StoreJoinLocal> storeJoinLocals = storeMapper.getStoreInfo();
        List<StoreInfoResponseDto> storeInfoResponseDtos = new ArrayList<>();

        if (storeJoinLocals.get(0) == null) return storeInfoResponseDtos;

        for (int i = 0; i < storeJoinLocals.size(); i++) {
            StoreInfoResponseDto temp = new StoreInfoResponseDto(storeJoinLocals.get(i));
            log.info(Long.toString(temp.getCnt()));
            storeInfoResponseDtos.add(temp);

        }


        return storeInfoResponseDtos;


    }


}
