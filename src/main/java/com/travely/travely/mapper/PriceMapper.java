package com.travely.travely.mapper;

import com.travely.travely.dto.price.PriceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PriceMapper {
    @Select("SELECT * FROM price")
    List<PriceDto> getPriceDto();
}
