package com.travely.travely.mapper;

import com.travely.travely.domain.Review;
import com.travely.travely.dto.review.ReviewDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapper {

    @Select("SELECT * FROM review WHERE review.userIdx = #{userIdx} ORDER BY reviewIdx")
    List<ReviewDto> findAll(@Param("userIdx") final Long userIdx);

    @Select("SELECT * FROM review WHERE review.userIdx = #{userIdx} AND review.reviewIdx = #{reviewIdx}")
    ReviewDto findByReviewIdx(@Param("userIdx") final Long userIdx, @Param("reviewIdx") final Long reviewIdx);

    @Insert("INSERT INTO review VALUES (#{review.reviewIdx}, #{review.storeIdx}, #{userIdx}, #{review.content}, #{review.like}, #{review.createAt})")
    void saveReview(@Param("userIdx") final Long userIdx, @Param("review") final Review review);

    @Update("UPDATE review SET content = #{review.content}, review.like= #{review.like}, createAt = #{review.createAt} " +
            "WHERE userIdx = #{userIdx} AND reviewIdx = #{review.reviewIdx}")
    void getUpdate(@Param("userIdx") final Long userIdx, @Param("review") final Review review);

    @Delete("DELETE FROM review WHERE userIdx = #{userIdx} AND reviewIdx = #{reviewIdx}")
    void deleteByReviewIdx(@Param("userIdx") final Long userIdx, @Param("reviewIdx") final Long reviewIdx);

}
