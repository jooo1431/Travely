package com.travely.travely.service;

import com.travely.travely.dto.reservation.ReserveJoinPayment;
import com.travely.travely.dto.reservation.ReserveJoinStore;
import com.travely.travely.mapper.OwnerMapper;
import com.travely.travely.util.typeHandler.ProgressType;
import com.travely.travely.util.typeHandler.StateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerMapper ownerMapper;

    //업주가 QR코드 리드시 상태변경해주기
    //QR을 리드 하는 상황은
    //현금은 현물 거래를 하고 업주가 QR리드를 한다.
    //카드는 이미 PG를 통해 결제가 완료되어 있는 상태이다.
    @Transactional
    public String changeReserveStateAndProgressUsingQR(final String reserveCode) {

        String msg;
        final ReserveJoinPayment reserveJoinPayment = ownerMapper.getReserveJoinPaymentFindByReserveCode(reserveCode);

        if(reserveJoinPayment!=null){
            //현금결제
            //reserve테이블의 예약완료 -> 보관중으로
            final long reserveIdx = reserveJoinPayment.getReserveIdx();
            final Timestamp depositTime = new Timestamp(System.currentTimeMillis());
            final StateType stateType = StateType.Archiving;
            ownerMapper.setStateToArchiveOnReserve(reserveIdx, depositTime, stateType);

            //payment테이블의 결제진행중 -> 결제완료로
            final long payIdx = reserveJoinPayment.getPayIdx();
            final ProgressType progressType = ProgressType.DONE;
            ownerMapper.setProgressToDoneOnPayment(payIdx, progressType);

            return msg="READ";
        }else{
            return msg="NO DATA";
        }


    }

    //큐알코드 리드할때 업주인지 아닌지 체크
    //업주면 true 아니면 false
    public boolean areYouOwner(final String reserveCode, final long ownerIdx) {
        final ReserveJoinStore reserveJoinStore = ownerMapper.getReserveJoinStoreFindByReserveCode(reserveCode);
        if(reserveJoinStore!=null){
            if (reserveJoinStore.getOwnerIdx() == ownerIdx) return true;
            else return false;
        }
        else return false;

    }

}