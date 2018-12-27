package com.travely.travely.mapper;

import com.travely.travely.domain.Baggage;
import com.travely.travely.domain.Payment;
import com.travely.travely.domain.Reserve;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.dto.reservation.ReserveJoinPayment;
import com.travely.travely.dto.reservation.ReserveJoinPaymentJoinStore;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationMapper {

    //예약 테이블 가져오기
    @Select("SELECT * FROM reserve WHERE userIdx = #{userIdx} AND NOT deleted = 1 AND NOT state = 3")
    Reserve getReserve(@Param("userIdx")final long userIdx);

    //예약 목록에 등록
    @Insert("INSERT INTO reserve (userIdx, storeIdx, startTime, endTime, state, price, deleted,reserveCode,depositTime,takeTime)" +
            " VALUES (#{reserve.userIdx},#{reserve.storeIdx},#{reserve.startTime},#{reserve.endTime},#{reserve.state},#{reserve.price},#{reserve.deleted},#{reserve.reserveCode},#{reserve.depositTime},#{reserve.takeTime})")
    @Options(useGeneratedKeys = true, keyProperty = "reserve.reserveIdx", keyColumn = "reserve.reserveIdx")
    void saveReservation(@Param("reserve") final Reserve reserve);

    //예약번호에 따른 짐 목록 등록
    @Insert("INSERT INTO baggage (reserveIdx,bagCount,bagType) VALUES (#{reserveIdx},#{bagDto.bagCount},#{bagDto.bagType})")
    @Options(useGeneratedKeys = true, keyColumn = "baggage.bagIdx")
    void saveBaggages(@Param("reserveIdx") final long reserveIdx, @Param("bagDto") BagDto bagDto);

    //예약번호에 따른 짐 목록 추출
    @Select("SELECT * FROM baggage WHERE reserveIdx = #{reserveIdx}")
    List<BagDto> getBagDto(@Param("reserveIdx") final long reserveIdx);

    //예약 여부 검색
    @Select("SELECT COUNT(reserveIdx) FROM reserve WHERE userIdx = #{userIdx} AND NOT state = 3 AND NOT deleted = 1")
    long getReservationCountFindByUserIdx(@Param("userIdx") final long userIdx);

    //예약 목록 삭제처리
    @Update("UPDATE reserve SET deleted = 1 WHERE userIdx = userIdx")
    void deleteReservation(@Param("userIdx") final long userIdx);

    //짐 사진 목록 추출
    @Select("SELECT bagImg FROM baggageImg WHERE reserveIdx = #{reserveIdx}")
    List<String> getBaggagesImgs(@Param("reserveIdx")final long reserveIdx);

    //결제목록에 예약내역 토대로 저장
    @Insert("INSERT INTO payment (payType, totalPrice, reserveIdx, progressType) VALUES(#{payment.payType},#{payment.totalPrice},#{payment.reserveIdx},#{payment.progressType})")
    @Options(useGeneratedKeys = true, keyColumn = "payment.payIdx", keyProperty = "payment.payIdx")
    void savePayment(@Param("payment")final Payment payment);

    //예약목록 JOIN 결제목록 JOIN 상가목록 JOIN 정보 불러오기
    @Select("SELECT * FROM reserve NATURAL JOIN payment, store WHERE NOT deleted = 1 AND userIdx = #{userIdx}")
    ReserveJoinPaymentJoinStore getReservePaymentStoreFindByUserIdx(@Param("userIdx")final long userIdx);

    //예약목록 JOIN 결제목록 정보 불러오기
    @Select("SELECT * FROM reserve NATURAL JOIN payment WHERE NOT deleted = 1 AND userIdx = #{userIdx}")
    ReserveJoinPayment getReservePaymentFindByUserIdx(@Param("userIdx")final long userIdx);

//    //예약목록 userIdx로 조회하기
//    @Select("SELECT * FROM reserve as r JOIN store as s ON r.storeIdx = s.storeIdx WHERE NOT r.state = 3 AND NOT r.deleted = 1 AND r.userIdx = #{userIdx}")
//
//
//    //예약 코드 + 사용자 id로 예약목록 검색
//    @Select("SELECT * FROM")
//    void getReservationFindByReserveCodeAndUserIdx();

}
