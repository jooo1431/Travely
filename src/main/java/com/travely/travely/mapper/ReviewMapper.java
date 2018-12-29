package com.travely.travely.mapper;

import com.travely.travely.domain.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Select("select * from review where storeIdx = #{storeIdx}")
    List<Review> findReviewsByStoreIdx(@Param("storeIdx") final Long storeIdx);
}
