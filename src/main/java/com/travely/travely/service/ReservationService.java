package com.travely.travely.service;

import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.dto.price.PriceDto;
import com.travely.travely.dto.reservation.ReservationRequest;
import com.travely.travely.dto.reservation.ReservationResponse;
import com.travely.travely.dto.store.StoreDto;
import com.travely.travely.mapper.PriceMapper;
import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.mapper.StoreMapper;
import com.travely.travely.util.typeHandler.PayType;
import com.travely.travely.util.typeHandler.StateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    public ReservationResponse saveReservation(final long userIdx, final ReservationRequest reservationRequest) {

        final long TEMP_KEY = -1;

        // 영업시간 외 예약하려는 경우
        //if (isBetweenOpenAndClose(reservationRequest)) return null;

        // 이미예약되있는경우
        if (isReservation(userIdx)) return null;

        // 기본사용시간 4시간미만
        // if (timeCheck(reservationRequest)) return null;
        // 짐갯수 0개인경우
        for (int i = 0; i < reservationRequest.getBagDtos().size(); i++) {
            if (reservationRequest.getBagDtos().get(i).getBagCount() <= 0) return null;
        }

        final UUID uuid = UUID.randomUUID();
        //결제 코드 생성 = 고유번호 앞 8자리
        final String reserveCode = uuid.toString().substring(0, 7);
        log.info("reserve Code : " + reserveCode);

        //결제 타입에 따라 state값 골라서 넣어주기 CASH, CARD
        StateType stateType;
        if (reservationRequest.getPayType().getValue() == PayType.CASH.getValue())
            stateType = StateType.ReserveOk;
        else
            stateType = StateType.PayOk;

        //가게 평점
        double like = storeMapper.getAvgLikeGetByStoreIdx(reservationRequest.getStoreIdx());

        //가격 책정
        long price = priceTag(reservationRequest);


        StoreDto storeDto = new StoreDto(storeMapper.getStoreJoinUsersFindByStoreIdx(reservationRequest.getStoreIdx()), like);
        ReservationResponse reservationResponse = new ReservationResponse(reservationRequest, reserveCode, storeDto, price, stateType);

        //예약목록 저장, 짐목록 저장
        Reserve reserve = new Reserve(userIdx, reservationRequest.getStoreIdx(), reservationRequest.getStartTime(), reservationRequest.getEndTime(), stateType, price, 0, reserveCode, null, null, reservationRequest.getPayType());
        reservationMapper.saveReservation(reserve);

        for (int i = 0; i < reservationRequest.getBagDtos().size(); i++) {
            reservationMapper.saveBaggages(reserve.getReserveIdx(), reservationRequest.getBagDtos().get(i));
        }

        //현금결제 아니면 결제완료후 결제테이블로 올려야함

        return reservationResponse;

    }

    @Transactional
    public String cancelReservation(final long userIdx) {
        String msg;
        try {
            //예약이 있는지?
            long is = reservationMapper.getReservationCountFindByUserIdx(userIdx);
            if (is == 0) {
                msg = "예약 내역 없음";
            } else {
                reservationMapper.deleteReservation(userIdx);
                msg = "예약 취소 성공";
            }

        } catch (Exception e) {
            msg = null;
            log.error(e.getMessage());
        }
        return msg;
    }

    public ReservationResponse getReservation(final long userIdx) {
        //예약현황에서 deleted컬럼이 0이고, 짐수거 상태가 아닌 컬럼을 셀렉해오자

        //보관 현황에서 봐야할것들
        //예약상태정보
        //맡기는 시간, 찾은 시간
        // 짐정보, 가격
        // 결제상태 , 결제타입
        // 짐사진
        // 가게 위치정보 (위치,주소,영업시간
        //가게 평점
        Reserve reserve = reservationMapper.getReserve(userIdx);
        //예약 내역이 없다면?
        if(reserve==null)
            return null;

        double like = storeMapper.getAvgLikeGetByStoreIdx(reserve.getStoreIdx());
        StoreDto storeDto = new StoreDto(storeMapper.getStoreJoinUsersFindByStoreIdx(userIdx), like);
        List <BagDto> bagDtos = reservationMapper.getBagDto(reserve.getReserveIdx());
        List <String> bagImgs = reservationMapper.getBaggagesImgs(reserve.getReserveIdx());
        ReservationResponse reservationResponse = new ReservationResponse(reserve,bagDtos, reserve.getReserveCode(), storeDto, reserve.getPrice(), reserve.getState(),bagImgs);

        return reservationResponse;
    }


    // 영업시간 외 체크
    private boolean isBetweenOpenAndClose(final ReservationRequest reservationRequest) {

        final Store store = storeMapper.getStoreFindByStoreIdx(reservationRequest.getStoreIdx());

        //업체 오픈 시간 및 종료시간
        final String sOpenTime = store.getOpenTime();
        final String sCloseTime = store.getCloseTime();

        // 사용자가 입력한 예약 및 종료 시간
        final Timestamp startTime = reservationRequest.getStartTime();
        final Timestamp endTime = reservationRequest.getEndTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //예약날짜의 시작 날짜및 종료날짜
        String sYmdhhmm = simpleDateFormat.format(startTime.getTime());
        String eYmdhhmm = simpleDateFormat.format(endTime.getTime());

        //예약날 오픈 시간
        String soYmdhm = sYmdhhmm + " " + sOpenTime + ":00:000";
        //예약날 클로즈 시간
        String scYmdhm = sYmdhhmm + " " + sCloseTime + ":00:000";

        //예약 종료날 오픈 시간
        String eoYmdhm = eYmdhhmm + " " + sOpenTime + ":00:000";
        //예약 종료날 클로즈 시간
        String ecYmdhm = eYmdhhmm + " " + sCloseTime + ":00:000";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");

        try {
            //예약시작날 오픈시간 long값으로
            long soTime = sdf.parse(soYmdhm).getTime();
            //예약시작날 종료시간 long값으로
            long scTime = sdf.parse(scYmdhm).getTime();
            //예약종료날 오픈시간 long값으로
            long eoTime = sdf.parse(eoYmdhm).getTime();
            //예약종료날 종료시간 long값으로
            long ecTime = sdf.parse(ecYmdhm).getTime();

            //예약 시작 및 종료 시간 long값으로
            long reservedStartTime = startTime.getTime();
            long reservedEndTime = endTime.getTime();

            //영업시간 내에 해당하면 false 반환 아니면 true반환
            if ((soTime <= reservedStartTime) && (scTime >= reservedStartTime) && (eoTime <= reservedEndTime) && (ecTime >= reservedEndTime)) {
                return false;
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return true;
    }

    //기본사용시간 4시간 미만 컷
    private boolean timeCheck(ReservationRequest reservationRequest) {
        long diffHour = (reservationRequest.getEndTime().getTime() - reservationRequest.getStartTime().getTime()) / 1000 / 60 / 60;
        if (diffHour < 4)
            return true;
        else return false;
    }

    // 이미예약되있는경우
    private boolean isReservation(final long userIdx) {
        long cnt;
        if ((cnt = reservationMapper.getReservationCountFindByUserIdx(userIdx)) != 0) {
            log.info("cnt : " + cnt);
            return true;
        } else {
            log.info("cnt : " + cnt);
            return false;
        }

    }

    //가격계산
    private long priceTag(final ReservationRequest reservationRequest) {

        //예약 총 시간(ms)을 구하자
        final long mSec = reservationRequest.getEndTime().getTime() - reservationRequest.getStartTime().getTime();
        //총 시간(h) 변환
        long hour = mSec / 1000 / 60 / 60;


        //정시가 아니라 x시 y분 일경우 x+1시 로 계산
        if (hour * 1000 * 60 * 60 != mSec)
            hour++;


        //디비에서 해당하는 가격 불러와서 짐 갯수 만큼 곱해서 계산
        //만약 시간이 48시간 이상이라면?
        //-1 인덱스에 해당하는 가격을 곱해주자
        // ex) 50 -> 48기본 +  (50-48)/24+1 * -1의 값

        long additionalCount = 0;
        long additionalFee = 0;
        List<PriceDto> priceDtos = priceMapper.getPriceDto();
        long price = 0;
        for (int i = 0; i < priceDtos.size(); i++) {

            //추가금액은 건너뛰고
            if (priceDtos.get(i).getPriceIdx() == -1)
                continue;

            if (hour - priceDtos.get(i).getPriceIdx() < 0)
                break;
            if (hour == priceDtos.get(i).getPriceIdx()) {
                price = priceDtos.get(i).getPrice();
            } else if (i + 1 == priceDtos.size()) {
                //마지막 가격책정 시간보다 클때 --> 추가금액 계산을 해야하는 부분
                if ((hour - priceDtos.get(i).getPriceIdx()) % 24 == 0 && (hour - priceDtos.get(i).getPriceIdx()) / 24 > 0)
                    additionalCount = (hour - priceDtos.get(i).getPriceIdx()) / 24;
                else
                    additionalCount = (hour - priceDtos.get(i).getPriceIdx()) / 24 + 1;
                price = priceDtos.get(i).getPrice();
                additionalFee = priceDtos.get(0).getPrice();
            } else if (hour - priceDtos.get(i).getPriceIdx() > 0) {
                price = priceDtos.get(i + 1).getPrice();
            }
        }

        //가방갯수 구하자
        long totalBag = 0;
        List<BagDto> bagDtos = reservationRequest.getBagDtos();
        for (int i = 0; i < bagDtos.size(); i++) {
            totalBag += bagDtos.get(i).getBagCount();
        }

        //마지막으로 가격 책정
        price = price * totalBag + additionalFee * additionalCount * totalBag;

        return price;
    }
}
