package com.travely.travely.mapper;

import com.travely.travely.domain.Favorite;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FavoriteMapper {

    //상가정보페이지에서 userIdx, storeIdx를 받아서 해당 아이디가 누른 상점의 좋아요 여부를 검사
    @Select("SELECT * FROM Favorite WHERE userIdx = #{userIdx}, storeIdx=#{storeIdx}")
    Favorite getStateFavorite(@Param("userIdx") final long userIdx, @Param("storeIdx") final long storeIdx);

    @Update("UPDATE Favorite SET isFavorite = false WHERE userIdx = #{userIdx}, storeIdx=#{storeIdx}, isFavorite = #{isFavorite}")
    void updateStateFavorite(@Param("userIdx") final long userIdx, @Param("storeIdx") final long storeIdx, @Param("isFavorite") final int isFavorite);

    @Insert("INSERT INTO Favorite(userIdx, storeIdx,isFavorite) VALUES(#{userIdx},#{storeIdx},#{isFavorite})")
    @Options(useGeneratedKeys = true, keyColumn = "favoriteIdx") //ai에 해당하는 컬럼 써주는거임
    void insertStateFavorite(@Param("userIdx") final long userIdx, @Param("storeIdx") final long storeIdx, @Param("isFavorite") final int isFavorite);
}
