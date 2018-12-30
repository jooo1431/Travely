package com.travely.travely.service;

import com.travely.travely.domain.Store;
import com.travely.travely.dto.favorite.FavoriteStoreDto;
import com.travely.travely.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteMapper favoriteMapper;

    public List<FavoriteStoreDto> getFavoriteStore(final Long userIdx, final Long regionIdx){

        List<Long> favoriteStoreIdx = favoriteMapper.getFavoriteStoreIdx(userIdx);
        List<FavoriteStoreDto> favoriteStoreDtos = new ArrayList<>();

        for( Long storeIdx : favoriteStoreIdx ){

            Store store = favoriteMapper.getStoreInfo(storeIdx, regionIdx);

            if( store != null ) {
                String regionName = favoriteMapper.getRegionName(regionIdx);

                FavoriteStoreDto favoriteStoreDto = new FavoriteStoreDto(store);
                favoriteStoreDto.setRegionName(regionName);

                favoriteStoreDtos.add(favoriteStoreDto);
            }

        }

        return favoriteStoreDtos;
    }
}
