package com.travely.travely.mapper;

import com.travely.travely.domain.Baggages;
import com.travely.travely.domain.Reservation;
import com.travely.travely.dto.reservation.BagDto;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface ReservationMapper {

    //예약 목록에 등록
    @Insert("INSERT INTO reserve (reserveIdx, underUserIdx, storeIdx, startTime, endTime, state) " +
            "VALUES (#{reservation.reserveIdx},#{reservation.underUserIdx},#{reservation.storeIdx},#{reservation.startTime},#{reservation.endTime},#{reservation.stateType})")
    void saveReservation(@Param("reservation") final Reservation reservation);

    //예약번호에 따른 짐 목록 등록
    @Insert("INSERT INTO baggage (bagCount,bagType,reserveIdx) VALUES (#{bagDto.bagCount},#{bagDto.bagType},#{reserveIdx})")
    @Options(useGeneratedKeys = true, keyColumn = "baggage.bagIdx")
    void saveBaggages(@Param("reserveIdx") final String reserveIdx, @Param("bagDto") BagDto bagDto);

    //예약번호에 따른 짐 목록 추출
    @Select("SELECT * FROM baggage WHERE reserveIdx = #{reserveIdx}")
    List<Baggages> getBaggages(@Param("reserveIdx") final String reserveIdx);

    //예약 목록 검색
    @Select("SELETE FROM reservation WHERE underUserIdx = #{underUserIdx}")
    List<Reservation> getReservation(@Param("userIdx")final String underUserIdx);

    //예약 목록 삭제
    @Delete("DELETE FROM reservation WHERE underUserIdx = #{underUserIdx}")
    void deleteReservation(@Param("userIdx") final String underUserIdx);
}
