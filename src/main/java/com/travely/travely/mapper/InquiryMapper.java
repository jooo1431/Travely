package com.travely.travely.mapper;

import com.travely.travely.domain.Inquiry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InquiryMapper {

    @Insert("INSERT inquiry SET userIdx = #{userIdx}, content = #{inquiry.content}, createAt = #{inquiry.createAt}")
    void saveInquiry(@Param("userIdx") final Long userIdx, @Param("inquiry") final Inquiry inquiry);

    @Select("SELECT * FROM inquiry WHERE inquiryIdx = #{inquiryIdx}")
    Inquiry findByInquiryIdx(@Param("inquiryIdx") final Long inquiryIdx);
}
