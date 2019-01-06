package com.travely.travely.mapper;

import com.travely.travely.domain.BaggageImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BaggageImgMapper {

    //ReserveMapper List<Reserve> findUnderArvhiceReserveByOwnerIdx(@Param("ownerIdx") final Long ownerIdx); 에서 사용중
    @Select("SELECT * FROM baggageImg WHERE reserveIdx = #{reserveIdx}")
    List<BaggageImg> findBaggageImgByReserveIdx(@Param("reserveIdx") final Long reserveIdx);
}
