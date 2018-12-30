package com.travely.travely.service;

import com.travely.travely.domain.Favorite;
import com.travely.travely.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteMapper favoriteMapper;

    public int GetStateFavorite(final long userIdx, final long storeIdx) {

        final Favorite favorite = favoriteMapper.getStateFavorite(userIdx, storeIdx); //즐찾목록에 그 상가가 있는지 업는지 검사
        int isFavorite;

        if (favorite == null || favorite.getIsFavorite() == 0) { //한번도 등록안했거나 한번등록햇다 삭제하고 다시 등록할때
            isFavorite = 1;
            favoriteMapper.insertFavorite(userIdx, storeIdx, isFavorite);
            return 2;
        }
        else{
            isFavorite = 0;
            favoriteMapper.deleteFavorite(userIdx, storeIdx, isFavorite); //한번등록해서 삭제하는경우
            return 1;
        }


    }
}
