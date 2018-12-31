package com.travely.travely.mapper;

import com.travely.travely.domain.Users;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users(email,password,name,phone,auth) VALUES(#{users.email},#{users.password},#{users.name},#{users.phone},#{users.auth})")
    @Options(useGeneratedKeys = true, keyColumn = "users.userIdx", keyProperty = "users.userIdx")
    int saveUsers(@Param("users") final Users users);

    @Select("SELECT * FROM users WHERE email = #{email}")
    Users findByEmail(@Param("email") final String name);

    @Select("SELECT * FROM users WHERE userIdx = #{userIdx}")
    @Results(value = {
            @Result(property = "userIdx", column = "userIdx"),
            @Result(property = "reviews", javaType = List.class, column = "userIdx",
                    many = @Many(select = "com.travely.travely.mapper.ReviewMapper.findReviewsByUserIdx", fetchType = FetchType.LAZY)),
            @Result(property = "favorites", javaType = List.class, column = "userIdx",
                    many = @Many(select = "com.travely.travely.mapper.FavoriteMapper.findFavoriteByUserIdx", fetchType = FetchType.LAZY)),
            @Result(property = "reserves", javaType = List.class, column = "userIdx",
                    many = @Many(select = "com.travely.travely.mapper.ReservationMapper.findReserveByUserIdx", fetchType = FetchType.LAZY)),
    })
    Users findUserByUserIdx(@Param("userIdx")final Long userId);
}
