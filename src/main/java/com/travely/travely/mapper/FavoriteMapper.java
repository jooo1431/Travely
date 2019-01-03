package com.travely.travely.mapper;

import com.travely.travely.domain.Favorite;
import org.apache.ibatis.annotations.*;
import com.travely.travely.domain.Store;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;


@Mapper
public interface FavoriteMapper {

    @Insert("INSERT INTO favorite VALUES (NULL, #{favorite.userIdx}, #{favorite.storeIdx}, 1) ON DUPLICATE KEY UPDATE isfavorite = isfavorite*-1;")
    @Options(useGeneratedKeys = true, keyColumn = "favoriteIdx")
    void insertFavorite(@Param("favorite") Favorite favorite);

    //데베는 바뀌는데 스웨거에서 바뀐상태가 바로바로 안보여서 select문 추가해줌
    @Select("SELECT * FROM favorite WHERE userIdx=#{favorite.userIdx} and storeIdx=#{favorite.storeIdx}")
    Favorite findByFavorite(@Param("favorite") Favorite favorite);

    ////////////////////////////////////////////////////////////////////////


//    //상가정보 가져옴
//    @Select("SELECT * FROM favorite WHERE userIdx = #{userIdx}")
//    @Results(value = {
//            @Result(property = "store", javaType = Store.class, column = "storeIdx",
//                    one = @One(select = "com.travely.travely.mapper.StoreMapper.findStoreByStoreIdx", fetchType = FetchType.EAGER))
//    })
//    List<FavoriteStoreListResponseDto> getFavoriteStore(@Param("userIdx") final long userIdx);


}
