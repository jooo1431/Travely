package com.travely.travely.mapper;

import com.travely.travely.domain.RestWeek;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RestWeekMapper {
    @Select("select * from restweek where storeIdx = #{storeIdx}")
    List<RestWeek> findRestWeeksByStoreIdx(@Param("storeIdx") final Long storeIdx);
}
