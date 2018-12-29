package com.travely.travely.mapper;


import com.travely.travely.domain.BaggageImg;
import com.travely.travely.domain.Reserve;
import com.travely.travely.dto.reservation.ReserveJoinPayment;
import com.travely.travely.dto.reservation.ReservePaymentUsersDto;
import com.travely.travely.dto.reservation.ReserveJoinStore;
import com.travely.travely.util.typeHandler.ProgressType;
import com.travely.travely.util.typeHandler.StateType;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;

@Mapper
public interface OwnerMapper {


    //reserveCode로 reserve객체 가져오기
    @Select("SELECT * FROM reserve WHERE reserveCode = #{reserveCode} AND NOT state = 3 AND NOT state = 4")
    Reserve getReserve(@Param("reserveCode") final String reserveCode);

    //짐 사진 저장하기
    @Insert("INSERT INTO baggageImg (reserveIdx, bagImg) VALUE (#{baggageImg.reserveIdx}, #{baggageImg.bagImg})")
    void saveBaggagesImg(@Param("baggaeImg") final BaggageImg baggageImg);

    //reserveCode를 이용하여
    //현재 진행중인 예약 정보를 가져오기
    @Select("SELECT r.*, p.payIdx, p.payType, p.progressType, u.name, u.phone, u.profileImg FROM payment as p JOIN reserve as r, users as u WHERE p.reserveIdx = r.reserveIdx AND r.userIdx = u.userIdx AND NOT state = 3 AND NOT state = 4 AND reserveCode = #{reserveCode}")
    ReservePaymentUsersDto getReserveInfoFindByRerserveCode(@Param("reserveCode") final String reserveCode);

    //현재 진행형인 예약정보가 있는지 확인하기
    @Select("SELECT COUNT(*) FROM reserve WHERE reserveCode = #{reserveCode} AND NOT state = 3 AND NOT state = 4")
    long getReserveCountByReserveCode(@Param("reserveCode") final String reserveCode);

    //reserveCode(예약 코드)로 예약내역 JOIN 결제내역 결과 가지고 오기
    @Select("SELECT * FROM reserve NATURAL JOIN payment WHERE reserveCode = #{reserveCode} AND NOT state = 3 AND NOT state = 4")
    ReserveJoinPayment getReserveJoinPaymentFindByReserveCode(@Param("reserveCode") final String reserveCode);

    //현금결제가 끝났으니 reserve 의 예약상태(StateType)변경 맡긴시간(depositTime)저장
    @Update("UPDATE reserve SET depositTime = #{depositTime}, state = #{stateType} WHERE reserveIdx = #{reserveIdx}")
    void setStateToArchiveOnReserve(@Param("reserveIdx") final long reserveIdx, @Param("depositTime") final Timestamp depositTime, @Param("stateType") final StateType stateType);

    //현금결제가 끝났으니 payment의 결제(ProgressType)상태 변경
    @Update("UPDATE payment SET progressType = #{progressType} WHERE payIdx = #{payIdx}")
    void setProgressToDoneOnPayment(@Param("payIdx") final long payIdx, @Param("progressType") final ProgressType progressType);

    //업주확인을 위해 reserveCode를 이용하여 reserve Join store 정보를 가져온다
    @Select("SELECT * FROM reserve NATURAL JOIN store WHERE reserveCode = #{reserveCode}")
    ReserveJoinStore getReserveJoinStoreFindByReserveCode(@Param("reserveCode") final String reserveCode);
}
