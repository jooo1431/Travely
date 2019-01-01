package com.travely.travely.mapper;

import com.travely.travely.domain.Favorite;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FavoriteMapper {

    @Insert("INSERT INTO favorite VALUES (NULL, #{favorite.userIdx}, #{favorite.storeIdx}, 1) ON DUPLICATE KEY UPDATE isfavorite = isfavorite*-1;")
    @Options(useGeneratedKeys = true, keyColumn = "favoriteIdx")
        //ai에 해당하는 컬럼 써주는거임
    void insertFavorite(@Param("favorite") Favorite favorite);

    @Select("select * from favorite where userIdx=#{favorite.userIdx} and storeIdx=#{favorite.storeIdx}")
    Favorite findByFavorite(@Param("favorite") Favorite favorite);
}
