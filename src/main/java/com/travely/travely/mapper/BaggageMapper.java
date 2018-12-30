package com.travely.travely.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BaggageMapper {

    @Select("select ifnull(sum(bagCount),0) from baggage natural join reserve join store on store.storeIdx = reserve.storeIdx where store.storeIdx =#{storeIdx}")
    Long findSumCurrentBagByStoreIdx(@Param("storeIdx") final long storeIdx);

}
