package com.travely.travely.mapper;

import com.travely.travely.domain.Store;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    @Select("SELECT storeIdx FROM favorite WHERE favorite.userIdx = #{userIdx}")
    List<Long> getFavoriteStoreIdx(@Param("userIdx") final Long userIdx);

    @Select("SELECT * FROM store WHERE storeIdx = #{storeIdx} AND regionIdx = #{regionIdx}")
    Store getStoreInfo(@Param("storeIdx") final Long storeIdx, @Param("regionIdx") final Long regionIdx);

    @Select("SELECT regionName FROM region WHERE regionIdx = #{regionIdx}")
    String getRegionName(@Param("regionIdx") final Long regionIdx);

}
