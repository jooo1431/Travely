package com.travely.travely.mapper;

import com.travely.travely.domain.Baggage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BaggageMapper {

    @Select("SELECT * FROM baggage WHERE reserveIdx = #{reserveIdx}")
    List<Baggage> findBaggageByReserveIdx(@Param("reserveIdx") final Long reserveIdx);


    ///////////////////////////////

    @Select("select ifnull(sum(bagCount),0) from baggage natural join reserve join store on store.storeIdx = reserve.storeIdx where store.storeIdx =#{storeIdx}")
    Long findSumCurrentBagByStoreIdx(@Param("storeIdx") final long storeIdx);

}
