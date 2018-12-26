package com.travely.travely.service;

import com.travely.travely.domain.Reservation;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.reservation.BagDto;
import com.travely.travely.dto.reservation.ReservationRequest;
import com.travely.travely.dto.reservation.ReservationResponse;
import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.mapper.StoreMapper;
import com.travely.travely.util.typeHandler.StateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;
    private final StoreMapper storeMapper;

    @Transactional
    public ReservationResponse saveReservation(final ReservationRequest reservationRequest) {
        final UUID uuid = UUID.randomUUID();
        final String reserveIdx = uuid.toString();

        //최소-최대 결제 시간에 따른 승인-불허 로직 들어가야함
        //현재 4시간 이상일시 가격책정
        if(timeCheck(reservationRequest)){

        }else{

        }

        Reservation reservation = new Reservation(reserveIdx, reservationRequest, StateType.ReserveOk);
        reservationMapper.saveReservation(reservation);

        List<BagDto> bagDtoList = reservationRequest.getBagList();
        for (int i = 0; i < bagDtoList.size(); i++) {
            reservationMapper.saveBaggages(reserveIdx, bagDtoList.get(i));
        }

        //bagDtoList를 넘겨서 가격정보 넣어야함

        final long storeIdx = reservationRequest.getStoreIdx();
        Store store = storeMapper.getStoreFindByStoreIdx(storeIdx);

        return new ReservationResponse(reservation, bagDtoList, store, 4000);

    }

    @Transactional
    public boolean deleteReservation(final String userIdx){
        
        List<Reservation> reservations = reservationMapper.getReservation(userIdx);

        if(reservations==null)
            return false;

        try{
            reservationMapper.deleteReservation(userIdx);
            return true;
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return false;
        }

    }


    private boolean timeCheck(ReservationRequest reservationRequest){
        long diffHour= (reservationRequest.getEndTime().getTime()-reservationRequest.getStartTime().getTime())/1000/60/60;
        if(diffHour<4)
            return false;
        else return true;
    }

}
