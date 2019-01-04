package com.travely.travely.mapper;

import com.travely.travely.domain.Review;
import com.travely.travely.domain.Users;
import com.travely.travely.dto.review.ReviewStoreResponseDto;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ReviewMapper {

    @Select("SELECT * FROM review WHERE storeIdx = #{storeIdx} ORDER BY reviewIdx DESC LIMIT 5")
    @Results(value = {
            @Result(property = "reviewIdx", column = "reviewIdx"),
            @Result(property = "userIdx", column = "userIdx"),
            @Result(property = "users", javaType = Users.class, column = "userIdx",
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdx", fetchType = FetchType.EAGER))
    })
    List<Review> findReviewsByStoreIdx(@Param("storeIdx") final Long storeIdx);

    @Select("SELECT * FROM review WHERE storeIdx = #{storeIdx} AND reviewIdx < #{reviewIdx} ORDER BY reviewIdx DESC LIMIT 5")
    @Results(value = {
            @Result(property = "reviewIdx", column = "reviewIdx"),
            @Result(property = "userIdx", column = "userIdx"),
            @Result(property = "users", javaType = Users.class, column = "userIdx",
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdx", fetchType = FetchType.EAGER))
    })
    List<Review> findMoreReviewsByStoreIdx(@Param("storeIdx") final Long storeIdx, @Param("reviewIdx") final Long reviewIdx);

    @Insert("INSERT INTO review VALUES (NULL,#{review.storeIdx},#{review.userIdx},#{review.content},#{review.liked}, NOW()) " +
            "ON DUPLICATE KEY UPDATE content = #{review.content}, liked = #{review.liked}, createAt = NOW()")
    @SelectKey(resultType = Long.class,
            before = false,
            keyColumn = "reviewIdx",
            keyProperty = "review.reviewIdx",
            statement = "SELECT reviewIdx FROM review WHERE storeIdx=#{review.storeIdx} AND userIdx = #{review.userIdx}")
    @Options(useGeneratedKeys = true, keyColumn = "review.reviewIdx", keyProperty = "reviewIdx")
    void saveReview(@Param("review") final Review review);

    @Select("SELECT r.reviewIdx, r.content,r.liked,r.createAt, si.* FROM review as r LEFT JOIN (SELECT s.*,si.storeImgUrl FROM storeImg as si NATURAL JOIN store as s GROUP BY storeIdx) as si ON r.storeIdx = si.storeIdx WHERE r.userIdx = #{userIdx} ORDER BY r.reviewIdx DESC LIMIT 3")
    List<ReviewStoreResponseDto> findReviewsAndStoreByUserIdx(@Param("userIdx") final Long userIdx);

    @Select("SELECT r.reviewIdx, r.content,r.liked,r.createAt, si.* FROM review as r LEFT JOIN (SELECT s.*,si.storeImgUrl FROM storeImg as si NATURAL JOIN store as s GROUP BY storeIdx) as si ON r.storeIdx = si.storeIdx WHERE r.userIdx = #{userIdx} AND reviewIdx < #{reviewIdx} ORDER BY r.reviewIdx DESC LIMIT 3")
    List<ReviewStoreResponseDto> findMoreReviewsAndStoreByUserIdx(@Param("userIdx") final Long userIdx, @Param("reviewIdx") final Long reviewIdx);

    @Delete("DELETE FROM review WHERE userIdx = #{userIdx} AND reviewIdx = #{reviewIdx}")
    void deleteReviewByUserIdxAndReviewIdx(@Param("userIdx") final Long userIdx, @Param("reviewIdx") final Long reviewIdx);

    @Select("select * from review where storeIdx = #{storeIdx} and reviewIdx<#{reviewIdx} limit 5;")
    @Results(value = {
            @Result(property = "reviewIdx", column = "reviewIdx"),
            @Result(property = "users", javaType = Users.class, column = "userIdx",
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdx", fetchType = FetchType.EAGER))
    })
    List<Review> findReviewsByReviewIdxAndStoreIdx(@Param("reviewIdx") final Long reviewIdx, @Param("storeIdx") final Long StoreIdx);

    @Select("select * from review where userIdx = #{userIdx}")
    List<Review> findReviewsByUserIdx(@Param("userIdx") final Long userIdx);


    @Select("SELECT * FROM review WHERE reviewIdx = #{reviewIdx}")
    @Results(value = {
            @Result(property = "users", column = "userIdx", javaType = Long.class,
            one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdxFromReview", fetchType = FetchType.EAGER))
    })
    Review findReviewByReviewIdx(@Param("reviewIdx")final Long reviewIdx);


    @Select("SELECT * FROM review WHERE storeIdx = #{storeIdx}")
    @Results(value = {
            @Result(property = "users", column = "userIdx", javaType = Long.class,
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdxFromReview", fetchType = FetchType.EAGER))
    })
    List<Review> findMyReviewsByStoreIdx();
}
