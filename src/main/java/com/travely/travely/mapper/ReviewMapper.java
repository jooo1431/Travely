package com.travely.travely.mapper;

import com.travely.travely.domain.Review;
import com.travely.travely.domain.Users;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ReviewMapper {

    //StoreMapper Store findStoreByOwnerIdx(@Param("ownerIdx") final long ownerIdx); 에서 사용중
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

    @Delete("DELETE FROM review WHERE userIdx = #{userIdx} AND reviewIdx = #{reviewIdx}")
    void deleteReviewByUserIdxAndReviewIdx(@Param("userIdx") final Long userIdx, @Param("reviewIdx") final Long reviewIdx);

    @Select("select * from review where storeIdx = #{storeIdx} and reviewIdx<#{reviewIdx} ORDER BY reviewIdx DESC limit 5;")
    @Results(value = {
            @Result(property = "reviewIdx", column = "reviewIdx"),
            @Result(property = "userIdx", column = "userIdx"),
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
    Review findReviewByReviewIdx(@Param("reviewIdx") final Long reviewIdx);


    @Select("SELECT * FROM review WHERE storeIdx = #{storeIdx} AND userIdx = #{temp}")
    @Results(value = {
            @Result(property = "users", column = "userIdx", javaType = Long.class,
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdxFromReview", fetchType = FetchType.EAGER))
    })
    List<Review> findMyReviewsByStoreIdx();


    @Select("SELECT r.* FROM review as r INNER JOIN store as s WHERE r.storeIdx = s.storeIdx AND s.userIdx = #{ownerIdx} ORDER BY r.reviewIdx DESC LIMIT 5")
    @Results(value = {
            @Result(property = "userIdx", column = "userIdx"),
            @Result(property = "users", column = "userIdx", javaType = Long.class,
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdxFromReview", fetchType = FetchType.EAGER))
    })
    List<Review> findReviewsByOwnerIdx(@Param("ownerIdx") final Long ownerIdx);

    @Select("SELECT r.* FROM review as r INNER JOIN store as s WHERE r.storeIdx = s.storeIdx AND s.userIdx = #{ownerIdx} AND r.reviewIdx < #{reviewIdx} ORDER BY r.reviewIdx DESC LIMIT 5")
    @Results(value = {
            @Result(property = "userIdx", column = "userIdx"),
            @Result(property = "users", column = "userIdx", javaType = Users.class,
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdxFromReview", fetchType = FetchType.EAGER))
    })
    List<Review> findMoreReviewsByOwnerIdx(@Param("ownerIdx") final Long ownerIdx, @Param("reviewIdx") final Long reviewIdx);

    @Select("SELECT * FROM review WHERE storeIdx = #{storeIdx}")
    List<Review> findReviewsByStoreIdxForMyPage();
}
