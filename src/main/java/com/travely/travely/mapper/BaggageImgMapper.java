package com.travely.travely.mapper;

import com.travely.travely.domain.BaggageImg;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BaggageImgMapper {

    //ReserveMapper List<Reserve> findUnderArvhiceReserveByOwnerIdx(@Param("ownerIdx") final Long ownerIdx); 에서 사용중
    @Select("SELECT * FROM baggageImg WHERE reserveIdx = #{reserveIdx}")
    List<BaggageImg> findBaggageImgByReserveIdx(@Param("reserveIdx") final Long reserveIdx);

    //bagImageurl 저장 매퍼
    @Insert("INSERT INTO baggageImg (bagImg,reserveIdx) VALUES (#{url},#{reserveIdx})")
    @Options(useGeneratedKeys = true, keyColumn = "baggageImgIdx")
    void saveBagImgUrl(@Param("url") final String bagImg,@Param("reserveIdx")final Long reserveIdx);
}
