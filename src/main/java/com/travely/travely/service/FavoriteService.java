package com.travely.travely.service;

import com.travely.travely.domain.Favorite;
import com.travely.travely.dto.favorite.FavoriteResponseDto;
import com.travely.travely.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteMapper favoriteMapper;

    public FavoriteResponseDto updateFavorite(Long userIdx, Long storeIdx) {
        Favorite favorite = new Favorite(userIdx,storeIdx);
        favoriteMapper.insertFavorite(favorite);
        return new FavoriteResponseDto(favoriteMapper.findByFavorite(favorite));
    }
}
