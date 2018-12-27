package com.travely.travely.mapper;

import com.travely.travely.domain.Payment;
import com.travely.travely.domain.Reserve;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.dto.reservation.ReserveJoinPayment;
import com.travely.travely.dto.reservation.ReserveJoinPaymentJoinStore;
import com.travely.travely.dto.reservation.ReserveJoinStore;
import com.travely.travely.util.typeHandler.ProgressType;
import com.travely.travely.util.typeHandler.StateType;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface OwnerMapper {

    //예약 테이블 가져오기
    @Select("SELECT * FROM reserve WHERE userIdx = #{userIdx} AND NOT state = 4 AND NOT state = 3")
    Reserve getReserve(@Param("userIdx") final long userIdx);

    //예약 목록에 등록
    @Insert("INSERT INTO reserve (userIdx, storeIdx, startTime, endTime, state, price,reserveCode,depositTime,takeTime)" +
            " VALUES (#{reserve.userIdx},#{reserve.storeIdx},#{reserve.startTime},#{reserve.endTime},#{reserve.state},#{reserve.price},#{reserve.reserveCode},#{reserve.depositTime},#{reserve.takeTime})")
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
    @Select("SELECT COUNT(reserveIdx) FROM reserve WHERE userIdx = #{userIdx} AND NOT state = #{takeOff} AND NOT state = #{cancel}")
    long getReservationCountFindByUserIdx(@Param("userIdx") final long userIdx, @Param("takeOff") final StateType takeOff, @Param("cancel") final StateType cancel);

    //예약 목록 삭제처리
    @Update("UPDATE reserve SET state = #{cancel} WHERE userIdx = #{userIdx}")
    void deleteReservation(@Param("userIdx") final long userIdx, @Param("cancel") final StateType cancel);

    //결제 목록 취소 처리
    @Update("UPDATE payment SET progressType = #{progressType} WHERE reserveIdx = #{reserveIdx}")
    void deletePayment(@Param("reserveIdx") final long reserveIdx, @Param("CANCEL") final ProgressType progressType);

    //짐 사진 목록 추출
    @Select("SELECT bagImg FROM baggageImg WHERE reserveIdx = #{reserveIdx}")
    List<String> getBaggagesImgs(@Param("reserveIdx") final long reserveIdx);

    //결제목록에 예약내역 토대로 저장
    @Insert("INSERT INTO payment (payType, totalPrice, reserveIdx, progressType) VALUES(#{payment.payType},#{payment.totalPrice},#{payment.reserveIdx},#{payment.progressType})")
    @Options(useGeneratedKeys = true, keyColumn = "payment.payIdx", keyProperty = "payment.payIdx")
    void savePayment(@Param("payment") final Payment payment);

    //예약목록 JOIN 결제목록 JOIN 상가목록 JOIN 정보 불러오기
    @Select("SELECT * FROM reserve NATURAL JOIN payment, store WHERE NOT state = #{cancel} AND userIdx = #{userIdx}")
    ReserveJoinPaymentJoinStore getReservePaymentStoreFindByUserIdx(@Param("userIdx") final long userIdx, @Param("cancel") final StateType cancel);

    //유저의 예약목록 JOIN 결제목록 정보 불러오기 예약취소를 제외한 정보 --> 사용자의 이용내역 보여줄때 사용하면 될듯
    @Select("SELECT * FROM reserve NATURAL JOIN payment WHERE NOT state = #{cancel} AND userIdx = #{userIdx}")
    ReserveJoinPayment getReservePaymentFindByUserIdx(@Param("userIdx") final long userIdx, @Param("cancel") final StateType cancel);

    //유저의 예약목록 JOIN 결제목록 정보 불러오기 예약취소, 수거를 제외한정보만
    @Select("SELECT * FROM reserve NATURAL JOIN payment WHERE NOT state = #{cancel} AND NOT state = #{takeOff} AND userIdx = #{userIdx}")
    ReserveJoinPayment getReservePaymentFindByUserIdxExceptCancelTakeOff(@Param("userIdx") final long userIdx, @Param("takeOff") final StateType takeOff, @Param("cancel") final StateType cancel);

    //매장에서 보관중인 짐 갯수 가져오기
    @Select("SELECT SUM(bagCount) as total FROM reserve NATURAL JOIN baggage WHERE state = 0 OR state = 1 OR state = 2 AND storeIdx = #{storeIdx}")
    long getTotalBagCountFindByStoreIdx(@Param("storeIdx") final long storeIdx);

    //reserveCode(예약 코드)로 예약내역 JOIN 결제내역 결과 가지고 오기
    @Select("SELECT * FROM reserve NATURAL JOIN payment WHERE reserveCode = #{reserveCode} AND NOT state = 3 AND NOT state = 4")
    ReserveJoinPayment getReserveJoinPaymentFindByReserveCode(@Param("reserveCode") final String reserveCode);


    //현금결제가 끝났으니 reserve 의 예약상태변경 맡긴시간저장
    @Update("UPDATE reserve SET depositTime = #{depositTime}, state = #{stateType} WHERE reserveIdx = #{reserveIdx}")
    void setStateToArchiveOnReserve(@Param("reserveIdx") final long reserveIdx, @Param("depositTime") final Timestamp depositTime, @Param("stateType") final StateType stateType);

    //현금결제가 끝났으니 payment의 progress상태 변경
    @Update("UPDATE payment SET progressType = #{progressType} WHERE payIdx = #{payIdx}")
    void setProgressToDoneOnPayment(@Param("payIdx") final long payIdx, @Param("progressType") final ProgressType progressType);

    //업주확인을 위해 reserveCode를 이용하여 reserve Join store 정보를 가져온다
    @Select("SELECT * FROM reserve NATURAL JOIN store WHERE reserveCode = #{reserveCode}")
    ReserveJoinStore getReserveJoinStoreFindByReserveCode(@Param("reserveCode") final String reserveCode);
}
