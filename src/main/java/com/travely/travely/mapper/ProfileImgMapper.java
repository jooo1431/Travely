package com.travely.travely.mapper;

import com.travely.travely.domain.ProfileImg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProfileImgMapper {
    @Insert("UPDATE users SET profileImg = #{profileImgUrl} WHERE userIdx = #{userIdx}")
    void updateProfileImg(@Param("userIdx") final Long userIdx, @Param("profileImgUrl") final String profileImgUrl);


    @Select("SELECT profileImg FROM users WHERE userIdx = #{userIdx}")
    String findByUserIdx(@Param("userIdx") final Long userIdx);

}
