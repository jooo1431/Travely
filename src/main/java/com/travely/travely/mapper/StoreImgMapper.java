package com.travely.travely.mapper;

import com.travely.travely.domain.StoreImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreImgMapper {

    @Select("SELECT * from storeImg where storeIdx =#{storeIdx}")
    List<StoreImg> findStoreImgsByStoreIdx(@Param("storeIdx") final Long storeIdx);
}
