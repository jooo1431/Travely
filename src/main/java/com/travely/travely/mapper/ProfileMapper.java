package com.travely.travely.mapper;

import com.travely.travely.domain.Profile;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

@Mapper
public interface ProfileMapper {

    @Select("SELECT * FROM users WHERE userIdx = #{userIdx}")
    Profile findByUserIdx(@Param("userIdx") final Long userIdx);

    @Update("UPDATE users SET users.name = #{profile.name}, phone = #{profile.phone}, email = #{profile.email} WHERE userIdx = #{userIdx};")
    void updateProfile(@Param("userIdx") final Long userIdx, @Param("profile") final Profile profile);

}
