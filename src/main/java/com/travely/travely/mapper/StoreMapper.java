package com.travely.travely.mapper;


import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Review;
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
            @Result(column = "storeIdx", property = "storeIdx"),
            @Result(column = "userIdx", property = "ownerIdx"),
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

    @Select("SELECT * FROM store WHERE userIdx = #{userIdx}")
    @Results(value = {
            @Result(column = "storeIdx", property = "storeIdx"),
            @Result(column = "userIdx", property = "ownerIdx"),
            @Result(property = "reviews", javaType = Review.class, column = "storeIdx", many = @Many(select = "com.travely.travely.mapper.ReviewMapper.findReviewsByStoreIdx", fetchType = FetchType.LAZY))
    })
    Store findStoreByUserIdx(@Param("userIdx") final Long userIdx);

    @Select("SELECT * FROM store inner join favorite on store.storeIdx = favorite.storeIdx WHERE favorite.userIdx = #{temp} and isFavorite = 1 and regionIdx = #{regionIdx}")
    @Results(value = {
            @Result(column = "storeIdx", property = "storeIdx"),
            @Result(column = "userIdx", property = "ownerIdx"),
            @Result(property = "storeImgs", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.StoreImgMapper.findStoreImgsByStoreIdx", fetchType = FetchType.EAGER)),
            @Result(property = "restWeeks", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.RestWeekMapper.findRestWeeksByStoreIdx", fetchType = FetchType.EAGER)),
            @Result(property = "users", javaType = Users.class, column = "userIdx",
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdx", fetchType = FetchType.EAGER))
    })
    Store findStoreByFavoriteUserIdx();

    @Select("SELECT store.*, @temp := #{userIdx} as temp FROM store INNER JOIN review WHERE review.storeIdx = store.storeIdx AND review.userIdx = #{userIdx} ORDER BY review.reviewIdx DESC LIMIT 5")
    @Results(value = {
            @Result(column = "storeIdx", property = "storeIdx"),
            @Result(column = "userIdx", property = "ownerIdx"),
            @Result(property = "storeImgs", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.StoreImgMapper.findStoreImgsByStoreIdx", fetchType = FetchType.EAGER)),
            @Result(property = "reviews", javaType = List.class, column = "{temp=temp, storeIdx=storeIdx}",
                    many = @Many(select = "com.travely.travely.mapper.ReviewMapper.findMyReviewsByStoreIdx", fetchType = FetchType.EAGER))
    })
    List<Store> findMyReviewOfStoreByUserIdx(@Param("userIdx")final Long userIdx);

    @Select("SELECT store.*,@temp := #{userIdx} as temp FROM store INNER JOIN review WHERE review.storeIdx = store.storeIdx AND review.userIdx = #{userIdx} AND review.reviewIdx < #{reviewIdx} ORDER BY review.reviewIdx DESC LIMIT 5")
    @Results(value = {
            @Result(column = "storeIdx", property = "storeIdx"),
            @Result(column = "userIdx", property = "ownerIdx"),
            @Result(property = "storeImgs", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.StoreImgMapper.findStoreImgsByStoreIdx", fetchType = FetchType.EAGER)),
            @Result(property = "reviews", javaType = List.class, column = "{temp=temp, storeIdx=storeIdx}",
                    many = @Many(select = "com.travely.travely.mapper.ReviewMapper.findMyReviewsByStoreIdx", fetchType = FetchType.EAGER))
    })
    List<Store> findMoreMyReviewOfStoreByUserIdx(@Param("userIdx")final Long userIdx,@Param("reviewIdx")final Long reviewIdx);


    @Select("SELECT * FROM store WHERE userIdx = #{ownerIdx}")
    @Results(value = {
            @Result(column = "storeIdx", property = "storeIdx"),
            @Result(column = "userIdx", property = "ownerIdx"),
            @Result(property = "reviews", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.ReviewMapper.findReviewsByStoreIdx", fetchType = FetchType.LAZY)),
            @Result(property = "storeImgs", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.StoreImgMapper.findStoreImgsByStoreIdx", fetchType = FetchType.LAZY)),
            @Result(property = "restWeeks", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.RestWeekMapper.findRestWeeksByStoreIdx", fetchType = FetchType.LAZY)),
            @Result(property = "reserves", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.ReservationMapper.findUnderPickupReserveByStoreIdx", fetchType = FetchType.EAGER))
    })
    Store findStoreByOwnerIdx(@Param("ownerIdx") final long ownerIdx);

    @Select("SELECT * FROM store WHERE userIdx = #{ownerIdx}")
    @Results(value = {
            @Result(column = "storeIdx", property = "storeIdx"),
            @Result(column = "userIdx", property = "ownerIdx"),
            @Result(property = "reserves", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.ReservationMapper.findUnderPickupReserveByStoreIdx", fetchType = FetchType.EAGER)),
            @Result(property = "reviews", javaType = List.class, column = "storeIdx",
                    many = @Many(select = "com.travely.travely.mapper.ReviewMapper.findReviewsByStoreIdxForMyPage", fetchType = FetchType.LAZY)),
            @Result(property = "users", javaType = Users.class, column = "userIdx",
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdxFromReview", fetchType = FetchType.EAGER))
    })
    Store findStoreByOwnerIdxForMyPage(@Param("ownerIdx") final long ownerIdx);
}

