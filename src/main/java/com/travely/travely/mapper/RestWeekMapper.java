package com.travely.travely.mapper;

import com.travely.travely.domain.RestWeek;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RestWeekMapper {

    //StoreMapper Store findStoreByStoreIdx(@Param("storeIdx") final long storeIdx); 에서 사용중
    @Select("select * from restweek where storeIdx = #{storeIdx}")
    List<RestWeek> findRestWeeksByStoreIdx(@Param("storeIdx") final Long storeIdx);
}
