package com.travely.travely.mapper;

import com.travely.travely.domain.Region;
import com.travely.travely.domain.Store;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface RegionMapper {

    @Select("SELECT * FROM region")
    @Results(value = {
            @Result(property = "regionIdx", column = "regionIdx"),
            @Result(property = "stores", javaType = List.class, column = "regionIdx",
                    many = @Many(select = "com.travely.travely.mapper.StoreMapper.findStoresByRegionIdx", fetchType = FetchType.LAZY))
    })
    List<Region> findAllRegion();


    @Select("SELECT * FROM region")
    @Results(value = {
            @Result(property = "regionIdx", column = "regionIdx"),
            @Result(property = "useridx", javaType = Long.class,column = "userIdx"),
            @Result(property = "stores", javaType = Store.class, column = "userIdx",
                    many = @Many(select = "com.travely.travely.mapper.StoreMapper.findFavoriteStore", fetchType = FetchType.LAZY))
    })
    List<Region> findAllRegions(@Param("userIdx") final Long useridx);


    //지역id로 지역명 찾는 쿼리
    @Select("SELECT * FROM region WHERE regionIdx=#{regionIdx}")
    Region findRegionByRegionIdx(@Param("regionIdx") final long regionIdx);
}
