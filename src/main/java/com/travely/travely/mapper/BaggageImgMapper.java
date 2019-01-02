package com.travely.travely.mapper;

import com.travely.travely.domain.BaggageImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BaggageImgMapper {

    @Select("SELECT * FROM baggageImg WHERE reserveIdx = #{reserveIdx}")
    List<BaggageImg> findBaggageImgByReserveIdx(@Param("reserveIdx") final Long reserveIdx);


}
