package com.travely.travely.service;

import com.travely.travely.domain.Payment;
import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.domain.Price;
import com.travely.travely.dto.reservation.ReserveRequestDto;
import com.travely.travely.dto.reservation.ReservationQR;
import com.travely.travely.dto.reservation.ReserveJoinPayment;
import com.travely.travely.dto.store.StoreDto;
import com.travely.travely.mapper.PriceMapper;
import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.mapper.StoreMapper;
import com.travely.travely.util.typeHandler.ProgressType;
import com.travely.travely.util.typeHandler.StateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;
    private final StoreMapper storeMapper;
    private final PriceMapper priceMapper;


    @Transactional
    public ReservationQR saveReservation(final long userIdx, final ReserveRequestDto reserveRequestDto) {

        List<Reserve> reserves = reservationMapper.findReserveByStoreIdx(reserveRequestDto.getStoreIdx());

        final Double like;
        Store store = storeMapper.getStoreFindByStoreIdx(reserveRequestDto.getStoreIdx());
        if (reserves != null) {
            reserves.get(0).getStore().checkReserveTime(reserveRequestDto);
            //if (reservationMapper.findRerserveCountByUserIdx(userIdx) > 0) throw new RuntimeException();
        }


        //가게평점
        like = store.getGrade();

        // 짐갯수 0개인경우
        reserveRequestDto.checkCount();

        //To-do
        // 이미예약되있는경우

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        final UUID uuid = UUID.randomUUID();
        //결제 코드 생성 = 고유번호 앞 8자리
        final String reserveCode = uuid.toString().substring(0, 7);

        //결제타입과 무관하게 일단 예약 완료 상태로 만든다.
        StateType stateType = StateType.ReserveOk;

        //가격 책정
        long price = priceTag(reserveRequestDto);

        StoreDto storeDto = StoreDto.builder()
                .ownerName(store.getUsers().getName())
                .openTime(store.getOpenTime())
                .longitude(store.getLongitude())
                .latitude(store.getLatitude())
                .closeTime(store.getCloseTime())
                .avgLike(like)
                .address(store.getAddress())
                .storeName(store.getStoreName())
                .storeIdx(store.getStoreIdx())
                .storeCall(store.getStoreCall())
                .build();
        ReservationQR reservationQR = new ReservationQR(reserveRequestDto, reserveCode, storeDto, price, stateType);


        //예약목록 저장 결제목록에 저장 진행중으로, 짐목록 저장
        Reserve saveReserve = Reserve.builder()
                .userIdx(userIdx)
                .storeIdx(reserveRequestDto.getStoreIdx())
                .startTime(new Timestamp(reserveRequestDto.getStartTime()))
                .endTime(new Timestamp(reserveRequestDto.getEndTime()))
                .state(stateType)
                .price(price)
                .reserveCode(reserveCode)
                .depositTime(null)
                .takeTime(null)
                .build();

        //여기서 예약목록에 저장
        reservationMapper.saveReservation(saveReserve);

        //일단 결제 진행중인 상태로 결제테이블에 저장할거임
        Payment payment = Payment.builder()
                .payType(reserveRequestDto.getPayType())
                .totalPrice(price)
                .reserveIdx(saveReserve.getReserveIdx())
                .progressType(ProgressType.ING)
                .build();

        //여기서 결제목록에 저장
        reservationMapper.savePayment(payment);

        //여기서 짐목록 저장
        for (int i = 0; i < reserveRequestDto.getBagDtos().size(); i++) {
            reservationMapper.saveBaggages(saveReserve.getReserveIdx(), reserveRequestDto.getBagDtos().get(i));
        }

        return reservationQR;

    }

    @Transactional
    public String cancelReservation(final long userIdx) {

        //예약 취소하면 결제테이블에 있는 것도 결제 취소로 전부 바꿔버린다.

        String msg;
        StateType cancel = StateType.Cancel;
        StateType takeOff = StateType.TakeOff;
        ProgressType progressType = ProgressType.CANCEL;

        try {
            //예약이 있는지?
            long isReserve = reservationMapper.getReservationCountFindByUserIdx(userIdx, takeOff, cancel);

            if (isReserve == 0) {
                msg = "예약 내역 없음";
            } else {
                //취소, 수거를 제외한 reserve join payment 테이블 정보 가져오기
                ReserveJoinPayment reserveJoinPayment = reservationMapper.getReservePaymentFindByUserIdxExceptCancelTakeOff(userIdx, takeOff, cancel);
                //수정할 payment의 reserveIdx 추출
                final long reserveIdx = reserveJoinPayment.getReserveIdx();

                reservationMapper.deleteReservation(reserveIdx, cancel);
                reservationMapper.deletePayment(reserveIdx, progressType);
                msg = "예약 취소 성공";
            }

        } catch (Exception e) {
            msg = null;
            log.error(e.getMessage());
        }
        return msg;
    }

//    public ReservationQR getReservation(final long userIdx) {
//        //예약현황에서 짐수거, 취소 상태가 아닌 컬럼을 셀렉해오자
//
//        StateType takeOff = StateType.TakeOff;
//        StateType cancel = StateType.Cancel;
//
//        //취소한것만 제외한 reserve Join payment 테이블의 결과를 받아오자. --> 예약내역이겠지
//
//
//        //예약 내역이 없다면?
//
//
//        //예약 내역을 클라에게 반환시켜줄 준비를하고
//
//        //반환 객체를 생성
//        ReservationQR reservationQR = ReservationQR.builder()
//                .startTime(reserveJoinPayment.getStartTime())
//                .endTime(reserveJoinPayment.getEndTime())
//                .payType(reserveJoinPayment.getPayType())
//                .stateType(reserveJoinPayment.getStateType())
//                .price(reserveJoinPayment.getPrice())
//                .reserveCode(reserveJoinPayment.getReserveCode())
//                .bagDtos(bagDtos)
//                .bagImgs(bagImgs)
//                .storeDto(storeDto)
//                .build();
//
//        //반환하자.
//        return reservationQR;
//    }

    //짐의 수용가능한량을 비교하여 불린값으로 반환하자
    public boolean isFull(final List<BagDto> bagDtos, final long limit) {

        long userBagCount = 0;
        for (int i = 0; i < bagDtos.size(); i++) {
            userBagCount += bagDtos.get(i).getBagCount();
        }

        //수용가능하면 트루
        if (userBagCount <= limit)
            return true;

        return false;
    }

    //수용가능한 짐의 수를 반환한다.
    public long checkLimit(final long storeIdx) {

        StateType cancel = StateType.Cancel;
        StateType takeOff = StateType.TakeOff;

        //먼저 총 보관 한도를 구한다.
        Store store = storeMapper.getStoreFindByStoreIdx(storeIdx);
        long limit = store.getLimit();
        //현재 보관중인 짐의 총 갯수를 가져오자
        long currentBagCount = reservationMapper.getTotalBagCountFindByStoreIdx(storeIdx);


        //보관할수 있는 갯수를 구하자 0보다 작으면 그냥 리젝시키자.
        long canTakeCount = limit - currentBagCount;

        if (canTakeCount <= 0)
            return 0;
        else
            return canTakeCount;
    }


    //가격계산 --> 가격반환
    private long priceTag(final ReserveRequestDto reserveRequestDto) {

        //예약 총 시간(ms)을 구하자
        final long mSec = reserveRequestDto.getEndTime() - reserveRequestDto.getStartTime();
        //총 시간(h) 변환
        Long hour = mSec / 1000 / 60 / 60;
        //정시가 아니라 x시 y분 일경우 x+1시 로 계산
        if (hour * 1000 * 60 * 60 != mSec)
            hour++;
        //계산의 기본단위 변수
        Long unit = 0L;
        List<Price> prices = priceMapper.getAllPrice();
        for (int i = 0; i < prices.size() - 1; i++) {
            unit = prices.get(i).compareHour(hour, unit);
        }
        //계산을 위해 가방의 갯수를 구해야함.
        final Long count = reserveRequestDto.getBagsCount();

        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        //가격계산
        Long temp = unit * count;

        if (hour > prices.get(prices.size() - 1).getPriceIdx()) {
            //초과시간 계산
            Long overTime = prices.get(prices.size() - 1).getDiffHour(hour);

            //초과시간 가격계산
            Long overTemp = overTime * count * prices.get(prices.size() - 1).getPrice();
            temp = temp + overTemp;
        }

        return temp;
    }
}
