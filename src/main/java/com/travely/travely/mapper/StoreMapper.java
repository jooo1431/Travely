package com.travely.travely.mapper;


import com.travely.travely.domain.Store;
import com.travely.travely.domain.Users;
import com.travely.travely.dto.store.StoreJoinUsersDto;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface StoreMapper {

    @Select("SELECT u.name, s.storeName, s.address, s.storeCall, s.latitude, s.longitude, s.openTime, s.closeTime, s.storeIdx FROM store AS s JOIN users AS u ON s.ownerIdx = u.userIdx WHERE s.storeIdx = #{storeIdx}")
    StoreJoinUsersDto getStoreJoinUsersFindByStoreIdx(@Param("storeIdx") final long storeIdx);

    @Select("SELECT IFNULL(AVG(r.like),0) FROM store as s INNER JOIN review as r ON s.storeIdx=r.storeIdx WHERE s.storeIdx = #{storeIdx}")
    double getAvgLikeGetByStoreIdx(@Param("storeIdx") final long storeIdx);

    @Select("SELECT * FROM store WHERE storeIdx = #{storeIdx}")
    @Results(value = {
            @Result(property = "reviews", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.ReviewMapper.findReviewsByStoreIdx", fetchType = FetchType.LAZY)),
            @Result(property = "storeImgs", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.StoreImgMapper.findStoreImgsByStoreIdx", fetchType = FetchType.LAZY)),
            @Result(property = "restWeeks", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.RestWeekMapper.findRestWeeksByStoreIdx", fetchType = FetchType.LAZY)),
            @Result(property = "users", javaType = Users.class, column = "userIdx",
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdx", fetchType = FetchType.EAGER))
    })
    Store findStoreByStoreIdx(@Param("storeIdx") final long storeIdx);

    @Select("select * from store where regionIdx = #{regionIdx}")
    List<Store> findStoresByRegionIdx(@Param("regionIdx") final long regionIdx);

}

