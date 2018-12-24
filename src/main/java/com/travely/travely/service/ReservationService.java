package com.travely.travely.service;

import com.travely.travely.domain.Baggages;
import com.travely.travely.domain.Reservation;
import com.travely.travely.dto.reservation.BagDto;
import com.travely.travely.dto.reservation.ReservationRequest;
import com.travely.travely.dto.reservation.ReservationResponse;
import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.util.TypeUtil;
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
        final int state = TypeUtil.ReservationState.결제완료.ordinal();
        Reservation reservation = new Reservation(reserveIdx, reservationRequest, state);
        reservationMapper.saveReservation(reservation);

        List<Baggages> baggages = reservationMapper.getBaggages(reserveIdx);
        List<BagDto> bagDtos=new ArrayList<>();
        for(int i=0;i<baggages.size();i++){
            BagDto temp = new BagDto(baggages.get(i));
            bagDtos.add(temp);
        }
        return new ReservationResponse(reservation,bagDtos);

    }
}
