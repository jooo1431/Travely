package com.travely.travely.service;


import com.travely.travely.domain.Reservation;
import com.travely.travely.domain.Store;
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

import java.util.ArrayList;
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
        //결제 고유번호생성
        final String reserveIdx = uuid.toString();
        //결제 코드 생성 = 고유번호 앞 8자리
        final String reserveCode = reserveIdx.substring(0,7);
        //최소-최대 결제 시간에 따른 승인-불허 로직 들어가야함
        //현재 4시간 이상일시 가격책정
        if(timeCheck(reservationRequest)){

        }else{

        }

        return null;

    }

    @Transactional
    public boolean deleteReservation(final String userIdx){

        Reservation reservation = reservationMapper.getReservation(userIdx);

        if(reservation==null)
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


    public ReservationResponse getReservation(final String userIdx){

        Reservation reservation = reservationMapper.getReservation(userIdx);

        if(reservation==null)
            return null;

        Store store = storeMapper.getStoreFindByStoreIdx(reservation.getStoreIdx());
        List<Baggages> baggages = reservationMapper.getBaggages(reservation.getReserveIdx());
        List<BagDto> bagDtos = new ArrayList<>();
        for(int i=-0;i<baggages.size();i++){
            BagDto temp = new BagDto(baggages.get(i));
            bagDtos.add(temp);
        }

        ReservationResponse reservationResponse = new ReservationResponse(reservation,bagDtos,store,9999);

        return reservationResponse;
    }

    private boolean timeCheck(ReservationRequest reservationRequest){
        long diffHour= (reservationRequest.getEndTime().getTime()-reservationRequest.getStartTime().getTime())/1000/60/60;
        if(diffHour<4)
            return false;
        else return true;
    }

}
