package com.travely.travely.service;

import com.travely.travely.domain.Reservation;
import com.travely.travely.dto.reservation.ReservationReqDto;
import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.util.TypeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;

    @Transactional
    public void saveReservation(final ReservationReqDto reservationReqDto){
        final UUID uuid = UUID.randomUUID();
        final String reserveIdx = uuid.toString();
        final int state = TypeUtil.ReservationState.예약완료.ordinal();
        Reservation reservation = new Reservation(reserveIdx,reservationReqDto,state);
        reservationMapper.saveReservation(reservation);
    }
}
