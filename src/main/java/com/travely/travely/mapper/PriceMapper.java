package com.travely.travely.mapper;

import com.travely.travely.domain.Price;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PriceMapper {
    @Select("SELECT * FROM price")
    List<Price> getAllPrice();
}
