package com.travely.travely.mapper;

import com.travely.travely.domain.Inquiry;
import com.travely.travely.domain.InquiryImg;
import org.apache.ibatis.annotations.*;

@Mapper
public interface InquiryMapper {

    @Insert({"INSERT inquiry SET userIdx = #{userIdx}, content = #{inquiry.content}, createAt = #{inquiry.createAt}",})
    @Options(useGeneratedKeys = true, keyColumn = "inquiry.inquiryIdx", keyProperty = "inquiry.inquiryIdx")
    Long saveInquiry(@Param("userIdx") final Long userIdx, @Param("inquiry") final Inquiry inquiry);

    @Insert("INSERT inquiryImg SET inquiryIdx = #{inquiryIdx}, inquiryImg = #{inquiryImg}")
    void saveInquiryImg(@Param("inquiryIdx") final Long inquiryIdx, @Param("inquiryImg") final String inquiryImg);
}

