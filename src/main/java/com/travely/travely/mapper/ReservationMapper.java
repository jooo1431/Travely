package com.travely.travely.mapper;

import com.travely.travely.domain.Payment;
import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.util.typeHandler.ProgressType;
import com.travely.travely.util.typeHandler.StateType;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ReservationMapper {

    ///save에 쓰이는 쿼리

    //reserve와 연결된 테이블 정보 불러오기
    @Select("SELECT * FROM reserve WHERE storeIdx = #{storeIdx} AND state < 3 ")
    @Results(value = {
            @Result(property = "reserveIdx", javaType = Long.class, column = "reserveIdx"),
            @Result(property = "storeIdx", javaType = Long.class, column = "storeIdx"),
            @Result(property = "userIdx", javaType = Long.class, column = "userIdx"),
            @Result(property = "baggages", javaType = List.class, column = "reserveIdx",
                    many = @Many(select = "com.travely.travely.mapper.BaggageMapper.findBaggageByReserveIdx", fetchType = FetchType.LAZY)),
            @Result(property = "baggageImgs", javaType = List.class, column = "reserveIdx",
                    many = @Many(select = "com.travely.travely.mapper.BaggageImgMapper.findBaggageImgByReserveIdx", fetchType = FetchType.LAZY)),
            @Result(property = "payment", javaType = Payment.class, column = "reserveIdx",
                    one = @One(select = "com.travely.travely.mapper.PaymentMapper.findPaymentByReserveIdx", fetchType = FetchType.LAZY)),
            @Result(property = "store", javaType = Store.class, column = "storeIdx",
                    one = @One(select = "com.travely.travely.mapper.StoreMapper.findStoreByStoreIdx", fetchType = FetchType.LAZY)),
            @Result(property = "users", javaType = Store.class, column = "userIdx",
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdx", fetchType = FetchType.LAZY))
    })
    List<Reserve> findReserveByStoreIdx(@Param("storeIdx") final Long storeIdx);

    //예약 목록에 등록
    @Insert("INSERT INTO reserve (userIdx, storeIdx, startTime, endTime, state, price,reserveCode,depositTime,takeTime)" +
            " VALUES (#{reserve.userIdx},#{reserve.storeIdx},#{reserve.startTime},#{reserve.endTime},#{reserve.state},#{reserve.price},#{reserve.reserveCode},#{reserve.depositTime},#{reserve.takeTime})")
    @Options(useGeneratedKeys = true, keyProperty = "reserve.reserveIdx", keyColumn = "reserve.reserveIdx")
    void saveReservation(@Param("reserve") final Reserve reserve);

    //결제목록에 예약내역 토대로 저장
    @Insert("INSERT INTO payment (payType, totalPrice, reserveIdx, progressType) VALUES(#{payment.payType},#{payment.totalPrice},#{payment.reserveIdx},#{payment.progressType})")
    @Options(useGeneratedKeys = true, keyColumn = "payment.payIdx", keyProperty = "payment.payIdx")
    void savePayment(@Param("payment") final Payment payment);

    //예약번호에 따른 짐 목록 등록
    @Insert("INSERT INTO baggage (reserveIdx,bagCount,bagType) VALUES (#{reserveIdx},#{bagDto.bagCount},#{bagDto.bagType})")
    @Options(useGeneratedKeys = true, keyColumn = "baggage.bagIdx")
    void saveBaggages(@Param("reserveIdx") final long reserveIdx, @Param("bagDto") BagDto bagDto);

    ///save에 쓰이는 쿼리


    ///delete에 쓰이는 쿼리
    //유저idx로 해당 스토어에 저장되어있는 예약중인내역을 확인해온다
    @Select("SELECT * FROM reserve WHERE userIdx = #{userIdx} AND state < 3")
    @Results(value = {
            @Result(property = "reserveIdx", javaType = Long.class, column = "reserveIdx"),
            @Result(property = "storeIdx", javaType = Long.class, column = "storeIdx"),
            @Result(property = "userIdx", javaType = Long.class, column = "userIdx"),
            @Result(property = "baggages", javaType = List.class, column = "reserveIdx",
                    many = @Many(select = "com.travely.travely.mapper.BaggageMapper.findBaggageByReserveIdx", fetchType = FetchType.LAZY)),
            @Result(property = "baggageImgs", javaType = List.class, column = "reserveIdx",
                    many = @Many(select = "com.travely.travely.mapper.BaggageImgMapper.findBaggageImgByReserveIdx", fetchType = FetchType.LAZY)),
            @Result(property = "payment", javaType = Payment.class, column = "reserveIdx",
                    one = @One(select = "com.travely.travely.mapper.PaymentMapper.findPaymentByReserveIdx", fetchType = FetchType.LAZY)),
            @Result(property = "store", javaType = Store.class, column = "storeIdx",
                    one = @One(select = "com.travely.travely.mapper.StoreMapper.findStoreByStoreIdx", fetchType = FetchType.LAZY)),
            @Result(property = "users", javaType = Store.class, column = "userIdx",
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdx", fetchType = FetchType.LAZY))
    })
    Reserve findReserveByUserIdx(@Param("userIdx") final Long userIdx);

    //예약 목록 삭제처리
    @Update("UPDATE reserve SET state = #{cancel} WHERE reserveIdx = #{reserveIdx}")
    void deleteReservation(@Param("reserveIdx") final long reserveIdx, @Param("cancel") final StateType stateType);

    //결제 목록 취소 처리
    @Update("UPDATE payment SET progressType = #{cancel} WHERE reserveIdx = #{reserveIdx}")
    void deletePayment(@Param("reserveIdx") final long reserveIdx, @Param("cancel") final ProgressType progressType);

    ///delete에 쓰이는 쿼리
    ///////////////////////////////////////////////

    //예약 조회용 쿼리
    ///////////////////////////////////////////
    @Select("SELECT * FROM reserve WHERE reserveCode = #{reserveCode} AND state < 3")
    @Results(value = {
            @Result(property = "reserveIdx", javaType = Long.class, column = "reserveIdx"),
            @Result(property = "storeIdx", javaType = Long.class, column = "storeIdx"),
            @Result(property = "userIdx", javaType = Long.class, column = "userIdx"),
            @Result(property = "baggages", javaType = List.class, column = "reserveIdx",
                    many = @Many(select = "com.travely.travely.mapper.BaggageMapper.findBaggageByReserveIdx", fetchType = FetchType.LAZY)),
            @Result(property = "baggageImgs", javaType = List.class, column = "reserveIdx",
                    many = @Many(select = "com.travely.travely.mapper.BaggageImgMapper.findBaggageImgByReserveIdx", fetchType = FetchType.LAZY)),
            @Result(property = "payment", javaType = Payment.class, column = "reserveIdx",
                    one = @One(select = "com.travely.travely.mapper.PaymentMapper.findPaymentByReserveIdx", fetchType = FetchType.LAZY)),
            @Result(property = "store", javaType = Store.class, column = "storeIdx",
                    one = @One(select = "com.travely.travely.mapper.StoreMapper.findStoreByStoreIdx", fetchType = FetchType.LAZY)),
            @Result(property = "users", javaType = Store.class, column = "userIdx",
                    one = @One(select = "com.travely.travely.mapper.UserMapper.findUserByUserIdx", fetchType = FetchType.LAZY))
    })
    Reserve findReserveByReserveCode(@Param("reserveCode") final String reserveCode);


}
