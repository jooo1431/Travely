package com.travely.travely.mapper;

import com.travely.travely.domain.Review;
import com.travely.travely.domain.Users;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ReviewMapper {

    @Select("select * from review where storeIdx = #{storeIdx} order by createAt desc limit 1")
    @Results(value = {
            @Result(property = "reviewIdx", column = "reviewIdx"),
            @Result(property = "users", javaType = Users.class, column = "userIdx",
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdx", fetchType = FetchType.EAGER))
    })
    List<Review> findReviewsByStoreIdx(@Param("storeIdx") final Long storeIdx);

    @Select("select * from review where storeIdx = #{storeIdx} and reviewIdx<#{reviewIdx} limit 5;")
    @Results(value = {
            @Result(property = "reviewIdx", column = "reviewIdx"),
            @Result(property = "users", javaType = Users.class, column = "userIdx",
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdx", fetchType = FetchType.EAGER))
    })
    List<Review> findReviewsByReviewIdxAndStoreIdx(@Param("reviewIdx")final Long reviewIdx,@Param("storeIdx")final Long StoreIdx);

    @Select("select * from review where userIdx = #{userIdx}")
    List<Review> findReviewsByUserIdx(@Param("userIdx") final Long userIdx);

}
