package com.travely.travely.mapper;

import com.travely.travely.domain.Baggage;
import com.travely.travely.domain.Reserve;
import com.travely.travely.dto.baggage.BagDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationMapper {

    //예약 목록에 등록
    @Insert("INSERT INTO reserve (userIdx, storeIdx, startTime, endTime, state, price, deleted,reserveCode,depositTime,takeTime)" +
            " VALUES (#{reserve.userIdx},#{reserve.storeIdx},#{reserve.startTime},#{reserve.endTime},#{reserve.state},#{reserve.price},#{reserve.deleted},#{reserve.reserveCode},#{reserve.depositTime},#{reserve.takeTime})")
    @Options(useGeneratedKeys = true, keyColumn = "reserve.reserveIdx",keyProperty = "reserve.reserveIdx")
        int saveReservation(@Param("reserve")final Reserve reserve);

    //예약번호에 따른 짐 목록 등록
    @Insert("INSERT INTO baggage (reserveIdx,bagCount,bagType) VALUES (#{reserveIdx},#{bagDto.bagCount},#{bagDto.bagType})")
    @Options(useGeneratedKeys = true, keyColumn = "baggage.bagIdx")
    void saveBaggages(@Param("reserveIdx") final long reserveIdx, @Param("bagDto") BagDto bagDto);

    //예약번호에 따른 짐 목록 추출
    @Select("SELECT * FROM baggage WHERE reserveIdx = #{reserveIdx}")
    List<Baggage> getBaggages(@Param("reserveIdx") final String reserveIdx);

    //예약 여부 검색
    @Select("SELECT COUNT(reserveIdx) FROM reserve WHERE userIdx = #{userIdx} AND NOT state = 3")
    long getReservationCountFindByUserIdx(@Param("userIdx")final long userIdx);

    //예약 목록 삭제
    @Delete("DELETE FROM reservation WHERE underUserIdx = #{underUserIdx}")
    void deleteReservation(@Param("userIdx") final String underUserIdx);
}
