package com.travely.travely.mapper;

import com.travely.travely.dto.store.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreMapper {

    @Select("SELECT regionName, COUNT(regionIdx) as cnt, regionIdx FROM store NATURAL JOIN region GROUP BY regionName ORDER BY regionName")
    List<StoreCount> getStoreCount();

    @Select("SELECT storeName, storeIdx, regionName, regionIdx FROM store NATURAL JOIN region WHERE regionIdx = #{regionIdx} ORDER BY regionName")
    List<StoreListResponseDto> getStoreList(@Param("regionIdx") final long regionIdx);

    @Select("SELECT * FROM store NATURAL JOIN region WHERE storeIdx = #{storeIdx}")
    StoreDetailsInfoDto getStoreInfo(@Param("storeIdx") final long storeIdx);

    @Select("SELECT storeIdx, storeName, storeImg FROM storeImg NATURAL JOIN store WHERE storeIdx = #{storeIdx}")
    List<StoreImgDto> getStoreDetailsImg(@Param("storeIdx") final long storeIdx);

    @Select("SELECT * FROM store NATURAL JOIN region WHERE storeIdx = #{storeIdx}")
    StoreDetailsResponseDto getStoreDetails(@Param("storeIdx") final long storeIdx);

}

