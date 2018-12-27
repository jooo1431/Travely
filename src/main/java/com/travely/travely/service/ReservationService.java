package com.travely.travely.service;

import com.travely.travely.domain.Payment;
import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.dto.price.PriceDto;
import com.travely.travely.dto.reservation.ReservationRequest;
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
    public ReservationQR saveReservation(final long userIdx, final ReservationRequest reservationRequest) {

        // 영업시간 외 예약하려는 경우
        if (isBetweenOpenAndClose(reservationRequest)) return null;
        log.info("@");
        // 이미예약되있는경우
        if (isReservation(userIdx)) return null;
        log.info("@");
        // 기본사용시간 4시간미만
        if (timeCheck(reservationRequest)) return null;
        log.info("@");
        // 짐갯수 0개인경우
        for (int i = 0; i < reservationRequest.getBagDtos().size(); i++) {
            if (reservationRequest.getBagDtos().get(i).getBagCount() <= 0) return null;
        }
        log.info("@");
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        final UUID uuid = UUID.randomUUID();
        //결제 코드 생성 = 고유번호 앞 8자리
        final String reserveCode = uuid.toString().substring(0, 7);
        log.info("reserve Code : " + reserveCode);

        //결제타입과 무관하게 일단 예약 완료 상태로 만든다.
        StateType stateType = StateType.ReserveOk;

        //가게 평점
        double like = storeMapper.getAvgLikeGetByStoreIdx(reservationRequest.getStoreIdx());

        //가격 책정
        long price = priceTag(reservationRequest);


        StoreDto storeDto = new StoreDto(storeMapper.getStoreJoinUsersFindByStoreIdx(reservationRequest.getStoreIdx()), like);
        ReservationQR reservationQR = new ReservationQR(reservationRequest, reserveCode, storeDto, price, stateType);


        //예약목록 저장 결제목록에 저장 진행중으로, 짐목록 저장
        Reserve reserve = Reserve.builder()
                .userIdx(userIdx)
                .storeIdx(reservationRequest.getStoreIdx())
                .startTime(reservationRequest.getStartTime())
                .endTime(reservationRequest.getEndTime())
                .state(stateType)
                .price(price)
                .reserveCode(reserveCode)
                .depositTime(null)
                .takeTime(null)
                .build();

        //여기서 예약목록에 저장
        reservationMapper.saveReservation(reserve);

        //일단 결제 진행중인 상태로 결제테이블에 저장할거임
        Payment payment = Payment.builder()
                .payType(reservationRequest.getPayType())
                .totalPrice(price)
                .reserveIdx(reserve.getReserveIdx())
                .progressType(ProgressType.ING)
                .build();

        //여기서 결제목록에 저장
        reservationMapper.savePayment(payment);

        //여기서 짐목록 저장
        for (int i = 0; i < reservationRequest.getBagDtos().size(); i++) {
            reservationMapper.saveBaggages(reserve.getReserveIdx(), reservationRequest.getBagDtos().get(i));
        }

        return reservationQR;

    }

    @Transactional
    public String cancelReservation(final long userIdx) {

        //예약 취소하면 결제테이블에 있는 것도 결제 취소로 전부 바꿔버린다.

        String msg;
        StateType cancel = StateType.Cancel;
        StateType takeOff = StateType.TakeOff;
        ProgressType CANCEL = ProgressType.CANCEL;

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
                reservationMapper.deletePayment(reserveIdx, CANCEL);
                msg = "예약 취소 성공";
            }

        } catch (Exception e) {
            msg = null;
            log.error(e.getMessage());
        }
        return msg;
    }

    public ReservationQR getReservation(final long userIdx) {
        //예약현황에서 짐수거, 취소 상태가 아닌 컬럼을 셀렉해오자

        StateType takeOff = StateType.TakeOff;
        StateType cancel = StateType.Cancel;

        //취소한것만 제외한 reserve Join payment 테이블의 결과를 받아오자. --> 예약내역이겠지
        ReserveJoinPayment reserveJoinPayment = reservationMapper.getReservePaymentFindByUserIdxExceptCancelTakeOff(userIdx, takeOff, cancel);

        //예약 내역이 없다면?
        if (reserveJoinPayment == null)
            return null;


        //예약 내역을 클라에게 반환시켜줄 준비를하고
        double like = storeMapper.getAvgLikeGetByStoreIdx(reserveJoinPayment.getStoreIdx());
        StoreDto storeDto = new StoreDto(storeMapper.getStoreJoinUsersFindByStoreIdx(reserveJoinPayment.getStoreIdx()), like);
        List<BagDto> bagDtos = reservationMapper.getBagDto(reserveJoinPayment.getReserveIdx());
        List<String> bagImgs = reservationMapper.getBaggagesImgs(reserveJoinPayment.getReserveIdx());

        //반환 객체를 생성
        ReservationQR reservationQR = ReservationQR.builder()
                .startTime(reserveJoinPayment.getStartTime())
                .endTime(reserveJoinPayment.getEndTime())
                .payType(reserveJoinPayment.getPayType())
                .stateType(reserveJoinPayment.getStateType())
                .price(reserveJoinPayment.getPrice())
                .reserveCode(reserveJoinPayment.getReserveCode())
                .bagDtos(bagDtos)
                .bagImgs(bagImgs)
                .storeDto(storeDto)
                .build();

        //반환하자.
        return reservationQR;
    }

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
        String soYmdhm = sYmdhhmm + " " + sOpenTime + ":00.0";
        //예약날 클로즈 시간
        String scYmdhm = sYmdhhmm + " " + sCloseTime + ":00.0";

        //예약 종료날 오픈 시간
        String eoYmdhm = eYmdhhmm + " " + sOpenTime + ":00.0";
        //예약 종료날 클로즈 시간
        String ecYmdhm = eYmdhhmm + " " + sCloseTime + ":00.0";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");

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
            log.info("영업시간 외");
            log.info(soYmdhm+" "+soTime);
            log.info(startTime+" "+reservedStartTime);
            log.info(scYmdhm+" "+scTime);
            log.info(eoYmdhm+" "+eoTime);
            log.info(endTime+" "+reservedEndTime);
            log.info(ecYmdhm+" "+ecTime);

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

        StateType takeOff = StateType.TakeOff;
        StateType cancel = StateType.Cancel;

        if ((cnt = reservationMapper.getReservationCountFindByUserIdx(userIdx, takeOff, cancel)) != 0) {
            log.info("cnt : " + cnt);
            return true;
        } else {
            log.info("cnt : " + cnt);
            return false;
        }

    }

    //가격계산 --> 가격반환
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
