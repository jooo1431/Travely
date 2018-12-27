package com.travely.travely.mapper;


import com.travely.travely.domain.Store;
import com.travely.travely.domain.StoreCount;

import com.travely.travely.domain.StoreImg;
import com.travely.travely.domain.StoreJoinLocal;
import com.travely.travely.dto.store.StoreJoinUsersDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface StoreMapper {

    @Select("SELECT * FROM store NATURAL JOIN local ORDER BY localName")
        //가나다 순으로 리스트에 들어감
    List<StoreJoinLocal> getEntireStoreInfo();

    @Select("SELECT localName, COUNT(localIdx) as cnt, localIdx FROM store NATURAL JOIN local GROUP BY localName ORDER BY localName")
    List<StoreCount> getStoreCount();

    @Select("SELECT u.name, s.storeName, s.address, s.storeCall, s.latitude, s.longitude, s.openTime, s.closeTime, s.storeIdx FROM store AS s JOIN users AS u ON s.ownerIdx = u.userIdx WHERE s.storeIdx = #{storeIdx}")
    StoreJoinUsersDto getStoreJoinUsersFindByStoreIdx(@Param("storeIdx") final long storeIdx);

    @Select("SELECT IFNULL(AVG(r.like),0) FROM store as s INNER JOIN review as r ON s.storeIdx=r.storeIdx WHERE s.storeIdx = #{storeIdx}")
    double getAvgLikeGetByStoreIdx(@Param("storeIdx") final long storeIdx);

    @Select("SELECT * FROM store WHERE storeIdx = #{storeIdx}")
    Store getStoreFindByStoreIdx(@Param("storeIdx") final long storeIdx);

    @Select("SELECT storeImg FROM storeImg WHERE storeIdx = #{storeIdx}")
    List<String> getStoreImgFindByStoreIdx(@Param("storeIdx") final long storeIdx);
}
