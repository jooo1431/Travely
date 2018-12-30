package com.travely.travely.mapper;

import com.travely.travely.domain.Favorite;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FavoriteMapper {

   // INSERT INTO Favorite(userIdx, storeIdx,isFavorite) VALUES(#{userIdx},#{storeIdx},#{isFavorite})
   // @Modifying
    //@Query("")
   // INSERT INTO favorite(userIdx, storeIdx,isFavorite) VALUES() ON DUPLICATE KEY UPDATE isFavorite = 1


    //상가정보페이지에서 userIdx, storeIdx를 받아서 해당 아이디가 누른 상점의 즐겨찾기 여부를 검사
    @Select("SELECT * FROM favorite WHERE userIdx = #{userIdx}, storeIdx=#{storeIdx}")
    Favorite getStateFavorite(@Param("userIdx") final long userIdx, @Param("storeIdx") final long storeIdx);

    //즐겨 찾기 삭제한다(isfavorite만 false(0)으로 바꿈 , 실제 테이블에서 delete는 안할거임)
    @Update("UPDATE favorite SET isFavorite = #{isFavorite} WHERE userIdx = #{userIdx}, storeIdx=#{storeIdx}")
    void deleteFavorite(@Param("userIdx") final long userIdx, @Param("storeIdx") final long storeIdx, @Param("isFavorite") final int isFavorite);

    //즐겨찾기 최초 등록하면 insert가 되는거고 삭제햇다가 다시 등록하면 update되서 isfavorite=1 이 되는거임
   // @Insert("INSERT INTO favorite(userIdx, storeIdx,isFavorite) VALUES(#{userIdx}, #{storeIdx}, #{isFavorite}) ON DUPLICATE KEY UPDATE isFavorite = #{isFavorite}")
    @Options(useGeneratedKeys = true, keyColumn = "favoriteIdx") //ai에 해당하는 컬럼 써주는거임
    void insertFavorite(@Param("userIdx") final long userIdx, @Param("storeIdx") final long storeIdx, @Param("isFavorite") final int isFavorite);
}
