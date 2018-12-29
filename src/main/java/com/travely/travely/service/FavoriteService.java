package com.travely.travely.service;

import com.travely.travely.domain.Favorite;
import com.travely.travely.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteMapper favoriteMapper;

    public int GetStateFavorite(final long userIdx, final long storeIdx){

        final Favorite favorite =  favoriteMapper.getStateFavorite(userIdx,storeIdx);
        int isfavorite;

        if(favorite != null) { //한번이상 좋아요의 상태를 바꾼경우
            if (favorite.getIsFavorite() == 1) { //좋아요인 경우
                isfavorite = 0;
                favoriteMapper.updateStateFavorite(userIdx, storeIdx,isfavorite); //좋아요를 취소해준다(false로 변경)
            }
            else{ //좋아요가 아닌경우
                isfavorite = 1;
                favoriteMapper.updateStateFavorite(userIdx, storeIdx,isfavorite); //좋아요로 바꿔준다(true로 변경)
            }
            return 1; // 즐겨찾기 상태 변경 성공
        }
        else{ //좋아요를 한번도 안누른 상태
            isfavorite = 1;
            favoriteMapper.insertStateFavorite(userIdx, storeIdx,isfavorite);
        }
        return 2;// 즐겨찾기에 추가 성공
    }
}
