package com.travely.travely.mapper;

import com.travely.travely.domain.Reservation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface ReservationMapper {

    //예약 목록에 올리기 위해서
    @Insert("INSERT INTO reserve (reserveIdx, userIdx, storeIdx, startDate, startDay, startTime, endDate, endDay, endTime, state) " +
            "VALUES (#{reservation.reserveIdx},#{reservation.userIdx},#{reservation.storeIdx},#{reservation.startDate},#{reservation.startDay},#{reservation.startTime},#{reservation.endDate},#{reservation.endDay},#{reservation.endTime},#{reservation.state})")
    void saveReservation(final Reservation reservation);

}
