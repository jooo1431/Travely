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

        final List<StoreListResponseDto> storeListResponseDtos = storeMapper.getStoreList(regionIdx); //전체 테이블정보를 받음
        if (storeListResponseDtos.get(0) == null) return storeListResponseDtos; //테이블정보 못받아오면 빈리스트 반환
        return storeListResponseDtos; //테이블정보 받았으면 리스트 제대로 반환
    }

    public StoreDetailsInfoDto getStoreInfo(final long storeIdx) {

        final StoreDetailsInfoDto storeInfo = storeMapper.getStoreInfo(storeIdx);
        if (storeInfo == null) return storeInfo;
        return storeInfo;
    }

    public List<StoreImgDto> getStoreDetailsImg(final long storeIdx) {

        final List<StoreImgDto> storeImgDtos = storeMapper.getStoreDetailsImg(storeIdx); //전체 테이블정보를 받음
        if (storeImgDtos.get(0) == null) return storeImgDtos; //테이블정보 못받아오면 빈리스트 반환
        return storeImgDtos; //테이블정보 받았으면 리스트 제대로 반환
    }

    public StoreDetailsResponseDto getStoreDetails(final long storeIdx) {

        StoreDetailsInfoDto storeInfo = getStoreInfo(storeIdx);
        if (storeInfo == null) return null;
        List<StoreImgDto> storeImgs = getStoreDetailsImg(storeIdx);

        StoreDetailsResponseDto storeDetailsResponseDto = new StoreDetailsResponseDto(storeImgs, storeInfo);

        return storeDetailsResponseDto;
    }
}
