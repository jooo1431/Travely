package com.travely.travely.service;

import com.travely.travely.domain.BaggageImg;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.dto.reservation.ReserveInfoDto;
import com.travely.travely.dto.reservation.ReserveJoinPayment;
import com.travely.travely.dto.reservation.ReservePaymentUsersDto;
import com.travely.travely.dto.reservation.ReserveJoinStore;
import com.travely.travely.mapper.OwnerMapper;
import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.util.typeHandler.ProgressType;
import com.travely.travely.util.typeHandler.StateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerMapper ownerMapper;
    private final ReservationMapper reservationMapper;
    private final S3FileUploadService s3FileUploadService;

    /**
     * QR 코드 읽고 예약정보 보여주기
     * reserveCode(예약 코드)로 현재 서비스 이용중인 사람의
     * 예약시작일 예약번호
     * 상세정보
     * 예약자
     * 예약자 연락처
     * 결제수단
     * 짐 타입
     * 가격 불러오기
     **/
    public ReserveInfoDto readReserveCode(final String reserveCode) {

        //예약코드로 사용자 정보 불러옴
        ReservePaymentUsersDto reservePaymentUsersDto = ownerMapper.getReserveInfoFindByRerserveCode(reserveCode);
        final long reserveIdx = reservePaymentUsersDto.getReserveIdx();

        //짐정보 불러옴
        List<BagDto> bagDtos = reservationMapper.getBagDto(reserveIdx);
        List<String> bagImgs = reservationMapper.getBaggagesImgs(reserveIdx);

        return new ReserveInfoDto(reservePaymentUsersDto, bagDtos, bagImgs);
    }


    //업주가 QR코드 리드시 상태변경해주기
    //QR을 리드 하는 상황은
    //현금은 현물 거래를 하고 업주가 QR리드를 한다.
    //카드는 이미 PG를 통해 결제가 완료되어 있는 상태이다.
    @Transactional
    public boolean changeStateSavePhoto(final String reserveCode, final MultipartFile[] multipartFiles) {

        final ReserveJoinPayment reserveJoinPayment = ownerMapper.getReserveJoinPaymentFindByReserveCode(reserveCode);
        final long reserveIdx = reserveJoinPayment.getReserveIdx();

        if (reserveJoinPayment != null) {

            //사진 저장 로직
            List<BaggageImg> baggageImgs = new ArrayList<>();

            try {
                for (int i = 0; i < multipartFiles.length; i++) {
                    String url = s3FileUploadService.upload(multipartFiles[i]);
                    BaggageImg baggageImg = new BaggageImg(reserveIdx, url);
                    baggageImgs.add(baggageImg);
                }
                for (int i = 0; i < baggageImgs.size(); i++) {
                    ownerMapper.saveBaggagesImg(baggageImgs.get(i));
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                return false;
            }
            //사진 저장 로직 끝


            //현금결제
            //reserve테이블의 예약완료 -> 보관중으로
            final Timestamp depositTime = new Timestamp(System.currentTimeMillis());
            final StateType stateType = StateType.Archiving;
            ownerMapper.setStateToArchiveOnReserve(reserveIdx, depositTime, stateType);

            //payment테이블의 결제진행중 -> 결제완료로
            final long payIdx = reserveJoinPayment.getPayIdx();
            final ProgressType progressType = ProgressType.DONE;
            ownerMapper.setProgressToDoneOnPayment(payIdx, progressType);

            return true;
        } else {
            return false;
        }


    }

    //큐알코드 리드할때 업주인지 아닌지 체크
    //업주면 true 아니면 false
    public boolean areYouOwner(final String reserveCode, final long ownerIdx) {
        final ReserveJoinStore reserveJoinStore = ownerMapper.getReserveJoinStoreFindByReserveCode(reserveCode);
        if (reserveJoinStore != null) {
            if (reserveJoinStore.getOwnerIdx() == ownerIdx) return true;
            else return false;
        } else return false;

    }

    //해당 업주에게 보관중인 짐 리스트를 보여줄때
    public void readList(final long ownerIdx){

    }


    //픽업 (State.TakeOff)할 때
    //


    //예약이 있는지 확인하여 1이면 true 0, n 개이면 false 반환
    public boolean isReserveByReserveCode(final String reserveCode) {
        long cnt = ownerMapper.getReserveCountByReserveCode(reserveCode);
        if (cnt == 1) return true;
        else if (cnt == 0) return false;
        else return false;
    }
}