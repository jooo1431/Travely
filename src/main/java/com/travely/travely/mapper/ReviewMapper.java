package com.travely.travely.mapper;

import com.travely.travely.domain.Review;
import com.travely.travely.dto.review.ReviewStoreResponseDto;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ReviewMapper {
    @Select("select * from review where storeIdx = #{storeIdx}")
    List<Review> findReviewsByStoreIdx(@Param("storeIdx") final Long storeIdx);

    @Insert("INSERT INTO review VALUES (NULL,#{review.storeIdx},#{review.userIdx},#{review.content},#{review.liked}, NOW()) " +
            "ON DUPLICATE KEY UPDATE content = #{review.content}, liked = #{review.liked}, createAt = NOW()")
    @SelectKey(resultType = Timestamp.class,
            before = false,
            keyColumn = "createAt",
            keyProperty = "review.createAt",
            statement = "SELECT createAt FROM review WHERE storeIdx=#{review.storeIdx} AND userIdx = #{review.userIdx}")
    @Options(useGeneratedKeys = true, keyColumn = "review.reviewIdx", keyProperty = "review.reviewIdx")
    void saveReview(@Param("review") final Review review);

    @Select("SELECT r.reviewIdx,r.content,r.liked,r.createAt,s.*,si.storeImgUrl FROM review as r JOIN store as s, (SELECT * FROM storeImg GROUP BY storeIdx) as si WHERE r.storeIdx = s.storeIdx AND s.storeIdx = si.storeIdx AND r.userIdx = #{userIdx}")
    List<ReviewStoreResponseDto> findReviewsAndStoreByUserIdx(@Param("userIdx") final Long userIdx);
}
