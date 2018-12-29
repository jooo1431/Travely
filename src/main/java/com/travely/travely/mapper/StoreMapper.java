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
    Store findStoreByStoreIdx(@Param("storeIdx") final long storeIdx);

    @Select("SELECT storeName, storeIdx, regionName, regionIdx FROM store NATURAL JOIN region WHERE regionIdx = #{regionIdx} ORDER BY regionName")
    List<StoreListResponseDto> findStoreListDto(@Param("regionIdx") final long regionIdx);

    @Select("SELECT storeImg,storeImgIdx FROM storeImg WHERE storeIdx = #{storeIdx}")
    List<StoreImageResponseDto> findStoreImageByStoreIdx(Long storeIdx);

    @Select("SELECT * FROM store WHERE storeIdx = #{storeIdx}")
    Store getStoreFindByStoreIdx(@Param("storeIdx") final long storeIdx);
}

