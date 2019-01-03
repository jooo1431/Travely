package com.travely.travely.service;

import com.travely.travely.domain.Favorite;
import com.travely.travely.dto.favorite.FavoriteStateResponseDto;
import com.travely.travely.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.travely.travely.dto.favorite.FavoriteStoreResponseDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteMapper favoriteMapper;

    public FavoriteStateResponseDto updateFavorite(Long userIdx, Long storeIdx) {
        Favorite favorite = new Favorite(userIdx, storeIdx);
        favoriteMapper.insertFavorite(favorite);
        return new FavoriteStateResponseDto(favoriteMapper.findByFavorite(favorite));
    }

//    public List<FavoriteStoreResponseDto> getFavoriteStoreList(Long userIdx){
//
//        List<FavoriteStoreListResponseDto> favoriteStoreResponseDtos = favoriteMapper.getFavoriteStore(userIdx);
//        favoriteStoreResponseDtos(favoriteMapper.getFavoriteStore(userIdx));
//
//
//        /*List<Favorite> favoriteList = favoriteMapper.getFavoriteStoreList(userIdx);//유저아이디를 넣으면 그 유저가 즐찾한 상가목록 가져옴
//        CommonConfig.getCheckedList(favoriteList); // 해당 유저의 즐찾목록이 없으면 null list가 들어감
//        List<FavoriteStoreResponseDto> favoriteStoreResponseDtos = new ArrayList<>();
//
//        for( Favorite favorite : favoriteList ){
//           favoriteStoreResponseDtos.add(new FavoriteStoreResponseDto(favoriteMapper.getFavoriteStoreInfo(favorite.getStoreIdx())));
//        }*/
//
//        return favoriteStoreResponseDtos;
//
//    }
}
