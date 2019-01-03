package com.travely.travely.mapper;

import com.travely.travely.domain.Region;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface RegionMapper {

    @Select("select * from region")
    @Results(value = {
            @Result(property = "regionIdx", column = "regionIdx"),
            @Result(property = "stores", javaType = List.class, column = "regionIdx",
                    many = @Many(select = "com.travely.travely.mapper.StoreMapper.findStoresByRegionIdx", fetchType = FetchType.LAZY))
    })
    List<Region> findAllRegion();

}
