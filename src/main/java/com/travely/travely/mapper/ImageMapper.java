package com.travely.travely.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ImageMapper {

    @Insert("UPDATE users SET profileImg = #{profileImgUrl} WHERE userIdx = #{userIdx}")
    void updateImg(@Param("userIdx") final Long userIdx, @Param("profileImgUrl") final String profileImgUrl);

//    @Insert("UPDATE inquiryImg SET inquiryImg = #{inquiryImgUrl} WHERE userIdx = #{userIdx}")
//    void updateInquiryImg(@Param("userIdx") final Long userIdx, @Param("inquiryImgUrl") final String inquiryImgUrl);
//
//    @Insert("UPDATE baggageImg SET bagImg = #{bagImgUrl} WHERE userIdx = #{userIdx}")
//    void updateBaggageImg(@Param("userIdx") final Long userIdx, @Param("bagImgUrl") final String bagImgUrl);

    @Select("SELECT profileImg FROM users WHERE userIdx = #{userIdx}")
    String findByUserIdx(@Param("userIdx") final Long userIdx);

}
