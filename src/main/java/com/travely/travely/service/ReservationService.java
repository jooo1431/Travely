package com.travely.travely.service;

import com.travely.travely.domain.Payment;
import com.travely.travely.domain.Price;
import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.reservation.ReserveResponseDto;
import com.travely.travely.dto.reservation.ReserveRequestDto;
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
    public ReserveResponseDto saveReservation(final long userIdx, final ReserveRequestDto reserveRequestDto) {

        List<Reserve> reserves = reservationMapper.findReserveByStoreIdx(reserveRequestDto.getStoreIdx());
        Store store = storeMapper.findStoreByStoreIdx(reserveRequestDto.getStoreIdx());

        // 짐갯수 0개인경우
        reserveRequestDto.checkCount();

        //가게의 보환할수 있는 리미트양 - 현재 맡긴양 - 내가 맡길양 >=0 이어야함
        Long limit = store.getLimit();

        //해당 업체에 진행중(예약+결제+보관)인 항목이 있다면
        if (reserves.size() != 0) {

            //user가 이미 예약 했는지 확인
            for (Reserve reserve : reserves) {
                reserve.checkReserved(userIdx);
            }

            //올바른 시간에 예약을 하려하는지
            reserves.get(0).getStore().checkReserveTime(reserveRequestDto);

            //해당 업체에서 보관할수 있는지 확인, 보관가능하면 보관 가능한 양을 반환, 보관 불가능하면 익셉션
            Long totalBagCount = 0L;
            for (Reserve reserve : reserves) {
                totalBagCount += reserve.getTotalBag();
            }
            limit = reserves.get(0).getStore().getSpace(totalBagCount);
        }

        //휴무일에 예약이 시작되거나 끝나는지 확인
        store.checkRestWeek(reserveRequestDto);

        //내가 가지고온 짐의 양과 비교하여 보관할수 있는지 확인
        reserveRequestDto.checkSpace(limit);

        //가게평점
        final Double like = store.getGrade();

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
        ReserveResponseDto reserveResponseDto = new ReserveResponseDto(reserveRequestDto, reserveCode, storeDto, price, stateType);

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

        return reserveResponseDto;

    }

    @Transactional
    public void cancelReservation(final long userIdx) {
        StateType cancelState = StateType.Cancel;
        ProgressType cancelProgress = ProgressType.CANCEL;
        //예약 취소하면 결제테이블에 있는 것도 결제 취소로 전부 바꿔버린다.
        //정상적으로 예약된게 있는지 확인

        Reserve reserve = reservationMapper.findReserveByUserIdx(userIdx);
        if (reserve == null) throw new RuntimeException();

        reservationMapper.deleteReservation(reserve.getReserveIdx(), cancelState);
        reservationMapper.deletePayment(reserve.getReserveIdx(), cancelProgress);
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
        final Long count = reserveRequestDto.gainBagsCount();

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

    //reserveCode로 예약정보 + 보관정보를 보자

}
