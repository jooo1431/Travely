package com.travely.travely.service;


import com.travely.travely.domain.StoreCount;
import com.travely.travely.domain.StoreJoinLocal;
import com.travely.travely.dto.store.StoreDetailsResponseDto;
import com.travely.travely.dto.store.StoreListResponseDto;
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

    public List<StoreListResponseDto> getEntireStoreList() {

        final List<StoreCount> storeCounts = storeMapper.getStoreCount();
        List<StoreListResponseDto> storeListResponseDtos = new ArrayList<>();//이거 null로 초기화하면 에러뜸

        if (storeCounts.get(0) == null) return storeListResponseDtos;

        for (int i = 0; i < storeCounts.size(); i++) {
            StoreListResponseDto temp = new StoreListResponseDto(storeCounts.get(i));
            storeListResponseDtos.add(temp);
        }

        return storeListResponseDtos;
    }

    public List<StoreDetailsResponseDto> getStoreInfoDetails() {

        final List<StoreJoinLocal> storeJoinLocals = storeMapper.getEntireStoreInfo(); //전체 테이블정보를 받음
        List<StoreDetailsResponseDto> storeDetailsResponseDtos = new ArrayList<>(); //내가 클라한테 보내줄 정보만 담을 리스트

        if (storeJoinLocals.get(0) == null) return storeDetailsResponseDtos; //테이블정보 못받아오면 빈리스트 반환
        for (int i = 0; i < storeJoinLocals.size(); i++) {
            StoreDetailsResponseDto temp = new StoreDetailsResponseDto(storeJoinLocals.get(i));
            storeDetailsResponseDtos.add(temp);
        }

        return storeDetailsResponseDtos; //테이블정보 받았으면 리스트 제대로 반환
    }
}
