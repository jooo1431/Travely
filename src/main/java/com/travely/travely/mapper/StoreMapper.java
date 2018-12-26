package com.travely.travely.mapper;


import com.travely.travely.domain.StoreCount;

import com.travely.travely.domain.StoreJoinLocal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreMapper {

    @Select("SELECT * FROM store NATURAL JOIN local ORDER BY localName") //가나다 순으로 리스트에 들어감
    List<StoreJoinLocal> getEntireStoreInfo();

    @Select("SELECT localName, COUNT(localIdx) as cnt, localIdx FROM store NATURAL JOIN local GROUP BY localName ORDER BY localName")
    List<StoreCount> getStoreCount();

    @Select("SELECT u.name, s.address, s.storeCall, s.latitude, s.longitude, s.openTime, s.closeTime, s.storeIdx FROM store AS s JOIN users AS u ON s.ownerIdx = u.userIdx WHERE s.storeIdx = #{storeIdx}")
    Store
}
