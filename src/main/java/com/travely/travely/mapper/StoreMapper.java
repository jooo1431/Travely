package com.travely.travely.mapper;


import com.travely.travely.domain.Store;
import com.travely.travely.dto.store.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreMapper {

    @Select("SELECT u.name, s.storeName, s.address, s.storeCall, s.latitude, s.longitude, s.openTime, s.closeTime, s.storeIdx FROM store AS s JOIN users AS u ON s.ownerIdx = u.userIdx WHERE s.storeIdx = #{storeIdx}")
    StoreJoinUsersDto getStoreJoinUsersFindByStoreIdx(@Param("storeIdx") final long storeIdx);

    @Select("SELECT IFNULL(AVG(r.like),0) FROM store as s INNER JOIN review as r ON s.storeIdx=r.storeIdx WHERE s.storeIdx = #{storeIdx}")
    double getAvgLikeGetByStoreIdx(@Param("storeIdx") final long storeIdx);

    @Select("SELECT * FROM store WHERE storeIdx = #{storeIdx}")
    Store getStoreFindByStoreIdx(@Param("storeIdx") final long storeIdx);

    @Select("SELECT storeImg FROM storeImg WHERE storeIdx = #{storeIdx}")
    List<String> getStoreImgFindByStoreIdx(@Param("storeIdx") final long storeIdx);

    @Select("SELECT storeName, storeIdx, regionName, regionIdx FROM store NATURAL JOIN region WHERE regionIdx = #{regionIdx} ORDER BY regionName")
    List<StoreListResponseDto> getStoreList(@Param("regionIdx") final long regionIdx);

    @Select("SELECT * FROM store NATURAL JOIN region WHERE storeIdx = #{storeIdx}")
    StoreDetailsInfoDto getStoreInfo(@Param("storeIdx") final long storeIdx);

    @Select("SELECT storeIdx, storeName, storeImg FROM storeImg NATURAL JOIN store WHERE storeIdx = #{storeIdx}")
    List<StoreImgDto> getStoreDetailsImg(@Param("storeIdx") final long storeIdx);

    @Select("SELECT * FROM store NATURAL JOIN region WHERE storeIdx = #{storeIdx}")
    StoreDetailsResponseDto getStoreDetails(@Param("storeIdx") final long storeIdx);

}

