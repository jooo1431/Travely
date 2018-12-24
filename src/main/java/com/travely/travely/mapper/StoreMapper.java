package com.travely.travely.mapper;

import com.travely.travely.domain.StoreJoinLocal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreMapper {
    @Select("SELECT *, COUNT(localIdx) as cnt FROM store NATURAL JOIN local GROUP BY localIdx")
    List<StoreJoinLocal> getStoreInfo();
}
