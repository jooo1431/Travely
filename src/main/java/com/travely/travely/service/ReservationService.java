package com.travely.travely.service;

import com.travely.travely.domain.Payment;
import com.travely.travely.domain.Price;
import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.reservation.PriceResponseDto;
import com.travely.travely.dto.reservation.ReserveRequestDto;
import com.travely.travely.dto.reservation.ReserveResponseDto;
import com.travely.travely.dto.reservation.ReserveViewResponseDto;
import com.travely.travely.dto.store.StoreDto;
import com.travely.travely.exception.NotFoundReserveException;
import com.travely.travely.mapper.PriceMapper;
import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.mapper.StoreMapper;
import com.travely.travely.util.typeHandler.ProgressType;
import com.travely.travely.util.typeHandler.StateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationMapper reservationMapper;
    private final StoreMapper storeMapper;
    private final PriceMapper priceMapper;

    @Transactional
    public ReserveResponseDto saveReservation(final long userIdx, final ReserveRequestDto reserveRequestDto) {

        final List<Reserve> reserves = reservationMapper.findReserveByStoreIdx(reserveRequestDto.getStoreIdx());
        final Store store = storeMapper.findStoreByStoreIdx(reserveRequestDto.getStoreIdx());

        //예약기능 On/Off 검사
        store.checkAvailable();

        //예약시간의 적합성 판단
        reserveRequestDto.checkTime();
        reserveRequestDto.checkCurrentTime();

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

            //해당 업체에서 보관할수 있는지 확인, 보관가능하면 보관 가능한 양을 반환, 보관 불가능하면 익셉션
            Long totalBagCount = 0L;
            for (Reserve reserve : reserves) {
                totalBagCount += reserve.getTotalBag();
            }
            limit = reserves.get(0).getStore().getSpace(totalBagCount);
        }

        //휴무일에 예약이 시작되거나 끝나는지 확인
        store.checkRestWeek(reserveRequestDto);

        //올바른 시간에 예약을 하려는지
        store.checkReserveTime(reserveRequestDto);

        //내가 가지고온 짐의 양과 비교하여 보관할수 있는지 확인
        reserveRequestDto.checkSpace(limit);

        //가게평점
        final Double like = store.getGrade();

        final UUID uuid = UUID.randomUUID();
        //결제 코드 생성 = 고유번호 앞 8자리
        final String reserveCode = uuid.toString().substring(0, 7);

        //결제타입과 무관하게 일단 예약 완료 상태로 만든다.
        final StateType stateType = StateType.RESERVED;

        //가격 책정
        long price = priceTag(reserveRequestDto);

        //리스폰스에 담길 가게정보
        final StoreDto storeDto = StoreDto.builder()
                .store(store)
                .build();
        final ReserveResponseDto reserveResponseDto = new ReserveResponseDto(reserveRequestDto, reserveCode, storeDto, price, stateType);

        //예약목록 저장 결제목록에 저장 진행중으로, 짐목록 저장
        final Reserve saveReserve = reserveResponseDto.toEntity(userIdx);

        //여기서 예약목록에 저장
        reservationMapper.saveReservation(saveReserve);

        //일단 결제 진행중인 상태로 결제테이블에 저장할거임
        final Payment payment = Payment.builder()
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
        final StateType cancelState = StateType.CANCEL;
        final ProgressType cancelProgress = ProgressType.CANCEL;
        //예약 취소하면 결제테이블에 있는 것도 결제 취소로 전부 바꿔버린다.
        //정상적으로 예약된게 있는지 확인

        final Reserve reserve = reservationMapper.findReserveByUserIdx(userIdx);
        if (reserve == null) throw new NotFoundReserveException();

        reservationMapper.updateReservation(reserve.getReserveIdx(), cancelState);
        reservationMapper.updatePayment(reserve.getReserveIdx(), cancelProgress);
    }

    public List<PriceResponseDto> getPrices() {
        return priceMapper.getAllPrice()
                .stream()
                .map(price -> new PriceResponseDto(price))
                .collect(Collectors.toList());
    }

    //reserveCode로 예약정보 + 보관정보를 보자
    public ReserveViewResponseDto getReserveMyInfo(final Long reserveIdx) {
        final Reserve reserve = reservationMapper.findReserveByReserveIdx(reserveIdx);
        final Store store = reserve.getStore();
        //예약내역이 없으면?
        if (reserve == null) throw new NotFoundReserveException();

        final StoreDto storeDto = StoreDto.builder()
                .store(store)
                .build();

        //가격단위와 단위에 해당하는 시간
        final List<Price> priceList = priceMapper.getAllPrice();
        final Long hour = priceList.get(0).getMillsecToHour(reserve.getStartTime().getTime(), reserve.getEndTime().getTime());
        final Long priceUnit = priceList.get(0).getPriceUnit(priceList, hour);
        final Long priceIdx = priceList.get(0).findPriceIdxByUnit(priceList, priceUnit);
        final Long extraChargeCount = priceList.get(0).getExtraChargeCount(hour, priceList.get(priceList.size() - 1).getPriceIdx());
        final Long extraCharge = priceList.get(0).getPrice();

        final ReserveViewResponseDto reserveViewResponseDto = ReserveViewResponseDto.builder()
                .reserve(reserve)
                .storeDto(storeDto)
                .priceIdx(priceIdx)
                .priceUnit(priceUnit)
                .extraCharge(extraCharge)
                .extraChargeCount(extraChargeCount)
                .build();

        return reserveViewResponseDto;
    }

    //가격계산 --> 가격반환
    private long priceTag(final ReserveRequestDto reserveRequestDto) {

        //계산의 기본단위 변수
        List<Price> priceList = priceMapper.getAllPrice();

        //계산에 쓰일 시간값
        final Long hour = priceList.get(0).getMillsecToHour(reserveRequestDto.getStartTime(), reserveRequestDto.getEndTime());
        //단위 가격 책정
        final Long unit = priceList.get(0).getPriceUnit(priceList, hour);
        //가방의 갯수.
        final Long count = reserveRequestDto.gainBagsCount();

        //추가시간 계산
        final Long extraChargeCount = priceList.get(0).getExtraChargeCount(hour, priceList.get(priceList.size() - 1).getPriceIdx());
        final Long extraCharge = priceList.get(0).getPrice();
        //총 가격
        final Long price = count * (unit + extraChargeCount * extraCharge);

        return price;
    }

}
