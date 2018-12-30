package com.travely.travely.mapper;

import com.travely.travely.dto.store.RegionResponseDto;
import com.travely.travely.dto.store.StoreListResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RegionMapper {

    @Select("SELECT regionName, COUNT(regionIdx) as cnt, regionIdx FROM store NATURAL JOIN region GROUP BY regionName ORDER BY regionName")
    List<RegionResponseDto> findRegionDto();

    @Select("SELECT storeName, storeIdx, regionName, regionIdx FROM region NATURAL JOIN store WHERE regionIdx = #{regionIdx} ORDER BY regionName")
    List<StoreListResponseDto> findStoreListDtoByRegionIdx(@Param("regionIdx") final long regionIdx);
}
