package com.travely.travely.service;

import com.travely.travely.domain.Payment;
import com.travely.travely.domain.Price;
import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.reservation.*;
import com.travely.travely.dto.store.StoreDto;
import com.travely.travely.exception.AlreadyExistsReserveException;
import com.travely.travely.exception.AuthenticationErrorException;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
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

        //예약여부 검사
        if (reservationMapper.findReserveCntByuserIdx(userIdx) >= 1) throw new AlreadyExistsReserveException();

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
        if (store.getReserves().size() != 0) {
            //해당 업체에서 보관할수 있는지 확인, 보관가능하면 보관 가능한 양을 반환, 보관 불가능하면 익셉션
            Long totalBagCount = 0L;
            for (Reserve reserve : store.getReserves()) {
                totalBagCount += reserve.getTotalBag();
            }
            limit = store.getSpace(totalBagCount);
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
        final String reserveCode = uuid.toString().substring(0, 7).toUpperCase();

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
        //예약 취소하면 결제테이블에 있는 것도 결제 취소로 전부 바꿔버린다.
        //정상적으로 예약된게 있는지 확인
        final Reserve reserve = reservationMapper.findReserveByUserIdx(userIdx);
        if (reserve == null) throw new NotFoundReserveException();

        reservationMapper.deleteReserveAndPaymentByReserveIdx(reserve.getReserveIdx(), StateType.CANCEL, ProgressType.CANCEL);
    }

    @Transactional
    public void cancelReservation() {
        reservationMapper.deleteReservationAndPayment(StateType.CANCEL, ProgressType.CANCEL);
    }

    public List<PriceResponseDto> getPrices() {
        return priceMapper.getAllPrice()
                .stream()
                .map(price -> new PriceResponseDto(price))
                .collect(Collectors.toList());
    }

    //현재 진행형인 예약정보 + 보관정보를 보자
    public ReserveViewResponseDto getReserveMyInfo(final Long userIdx) {
        final Reserve reserve = reservationMapper.findReserveByUserIdx(userIdx);
        //예약내역이 없으면?
        if (reserve == null) throw new NotFoundReserveException();

        return getReserveViewResponseDto(reserve);
    }

    public ReserveViewResponseDto getReserveMyInfoByReserveIdx(final Long userIdx, final Long reserveIdx) {
        final Reserve reserve = reservationMapper.findReserveByReserveIdx(reserveIdx);
        //예약내역이 없으면?
        if (reserve == null) throw new NotFoundReserveException();
        //userIdx가 다르면?
        if (reserve.getUserIdx() != userIdx) throw new AuthenticationErrorException();

        return getReserveViewResponseDto(reserve);
    }

    private ReserveViewResponseDto getReserveViewResponseDto(final Reserve reserve) {
        final Store store = reserve.getStore();
        final StoreDto storeDto = new StoreDto(store);

        //가격단위와 단위에 해당하는 시간
        final List<Price> priceList = priceMapper.getAllPrice();
        final Long hour = getMillsecToHour(reserve.getStartTime().getTime(), reserve.getEndTime().getTime());
        final Long priceUnit = getPriceUnit(priceList, hour);
        final Long priceIdx = findPriceIdxByUnit(priceList, priceUnit);
        final Long extraChargeCount = getExtraChargeCount(hour, priceList.get(priceList.size() - 1).getPriceIdx());
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
        final Long hour = getMillsecToHour(reserveRequestDto.getStartTime(), reserveRequestDto.getEndTime());
        //단위 가격 책정
        final Long unit = getPriceUnit(priceList, hour);
        //가방의 갯수.
        final Long count = reserveRequestDto.gainBagsCount();

        //추가시간 계산
        final Long extraChargeCount = getExtraChargeCount(hour, priceList.get(priceList.size() - 1).getPriceIdx());
        final Long extraCharge = priceList.get(0).getPrice();
        //총 가격
        final Long price = count * (unit + extraChargeCount * extraCharge);

        return price;
    }

    public ReserveFlagDto getReserveFlagDto(Long userIdx) {
        return new ReserveFlagDto(reservationMapper.findReserveByUserIdx(userIdx));
    }

    public Long getMillsecToHour(final Long start, final Long end) {
        Long hour = (end - start) / 1000 / 60 / 60;
        if (hour * 1000 * 60 * 60 != end - start)
            hour++;
        return hour;
    }

    public Long getPriceUnit(final List<Price> prices, final Long hour) {
        Long unit = 0L;
        for (int i = 0; i < prices.size(); i++) {
            unit = prices.get(i).compareHour(hour, unit);
        }
        return unit;
    }

    public Long getExtraChargeCount(final Long hour, final Long finalIdx) {
        Long extra = 0L;
        if (hour > finalIdx) {
            final Long temp = hour - finalIdx;
            extra = temp / 12;
            if (temp % 12 == 0) extra--;
        }
        return extra;
    }

    public Long findPriceIdxByUnit(final List<Price> prices, final Long unit) {
        for (Price price : prices) {
            if (price.getPrice() == unit) return price.getPriceIdx();
        }
        throw new RuntimeException();
    }
}
