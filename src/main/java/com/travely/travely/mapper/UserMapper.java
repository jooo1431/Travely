package com.travely.travely.mapper;

import com.travely.travely.domain.Users;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users(email,password,name,phone,auth) VALUES(#{users.email},#{users.password},#{users.name},#{users.phone},#{users.auth})")
    @Options(useGeneratedKeys = true, keyColumn = "users.userIdx", keyProperty = "users.userIdx")
    int saveUsers(@Param("users") final Users users);

    @Select("SELECT * FROM users WHERE email = #{email}")
    Users findByEmail(@Param("email") final String name);

    @Select("SELECT * FROM users WHERE userIdx = #{userIdx}")
    Users findUserByUserIdx(@Param("userIdx") final Long userIdx);
}
