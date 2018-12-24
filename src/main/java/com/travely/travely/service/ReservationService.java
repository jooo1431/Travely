package com.travely.travely.service;

import com.travely.travely.domain.Baggages;
import com.travely.travely.domain.Reservation;
import com.travely.travely.dto.reservation.BagDto;
import com.travely.travely.dto.reservation.ReservationRequest;
import com.travely.travely.dto.reservation.ReservationResponse;
import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.util.StateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;

    @Transactional
    public ReservationResponse saveReservation(final ReservationRequest reservationRequest) {
        final UUID uuid = UUID.randomUUID();
        final String reserveIdx = uuid.toString();
        final int state = StateType.결제완료.ordinal();
        Reservation reservation = new Reservation(reserveIdx, reservationRequest, state);
        reservationMapper.saveReservation(reservation);

        List<BagDto> bagDtoList = reservationRequest.getBagList();
        for(int i=0;i<bagDtoList.size();i++){
            reservationMapper.saveBaggages(reserveIdx,bagDtoList.get(i));
        }

        //가격정보 넣어야함

        return new ReservationResponse(reservation,bagDtoList);

    }
}
