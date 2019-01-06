package com.travely.travely.mapper;

import com.travely.travely.domain.Payment;
import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.baggage.BagDto;
import com.travely.travely.util.typeHandler.PayType;
import com.travely.travely.util.typeHandler.ProgressType;
import com.travely.travely.util.typeHandler.StateType;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ReservationMapper {

    ///save에 쓰이는 쿼리
    //reserve와 연결된 예약 정보 불러오기
    @Select("SELECT * FROM reserve WHERE storeIdx = #{storeIdx} AND state < 3")
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
    List<Reserve> findReserveStateUnderPickUpByStoreIdx(@Param("storeIdx") final Long storeIdx);


    //reserve와 연결된 예약 정보 불러오기
    @Select("SELECT r.* FROM reserve as r INNER JOIN store as s WHERE r.storeIdx = s.storeIdx AND s.userIdx = #{ownerIdx} AND r.state < 2")
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
    List<Reserve> findUnderArvhiceReserveByOwnerIdx(@Param("ownerIdx") final Long ownerIdx);

    //reserve와 연결된 보관 정보 불러오기
    @Select("SELECT * FROM reserve as r INNER JOIN store as s WHERE r.storeIdx = s.storeIdx AND s.userIdx = #{ownerIdx} AND state = 2")
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
    List<Reserve> findArchiveReserveByOwnerIdx(@Param("ownerIdx") final Long ownerIdx);

    @Select("select * from reserve where useridx=#{userIdx} order by reserveIdx desc limit 5;")
    @Results(value = {
            @Result(property = "reserveIdx", column = "reserveIdx"),
            @Result(property = "baggages", javaType = List.class, column = "reserveIdx",
                    many = @Many(select = "com.travely.travely.mapper.BaggageMapper.findBaggageByReserveIdx", fetchType = FetchType.EAGER)),
            @Result(property = "store", javaType = Store.class, column = "storeIdx",
                    one = @One(select = "com.travely.travely.mapper.StoreMapper.findStoreByStoreIdx", fetchType = FetchType.EAGER))
    })
    List<Reserve> findReserveByUserIdxOrderByReviewIdx(@Param("userIdx") final Long userIdx);


    @Select("select * from reserve where useridx=#{userIdx} and reserveidx<#{reserveIdx} limit 5;")
    @Results(value = {
            @Result(property = "reserveIdx", column = "reserveIdx"),
            @Result(property = "baggages", javaType = List.class, column = "reserveIdx",
                    many = @Many(select = "com.travely.travely.mapper.BaggageMapper.findBaggageByReserveIdx", fetchType = FetchType.EAGER)),
            @Result(property = "store", javaType = Store.class, column = "storeIdx",
                    one = @One(select = "com.travely.travely.mapper.StoreMapper.findStoreByStoreIdx", fetchType = FetchType.EAGER))
    })
    List<Reserve> findReserveByUserIdxAndReserveIdx(@Param("userIdx") final Long userIdx, @Param("reserveIdx") final Long reserveIdx);


    ///////////////////////////////////////////

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
    @Update("UPDATE reserve SET state = #{state} WHERE reserveIdx = #{reserveIdx}")
    void updateReservation(@Param("reserveIdx") final long reserveIdx, @Param("state") final StateType stateType);

    @Update("UPDATE reserve as r NATURAL JOIN  payment as p SET  r.state = #{state}, p.progressType = #{progress} WHERE reserveIdx = #{reserveIdx}")
    void deleteReserveAndPaymentByReserveIdx(@Param("reserveIdx") final Long reserveIdx ,@Param("state")final StateType stateType,@Param("progress")final ProgressType progressType);

    @Update("UPDATE reserve A natural JOIN payment B\n" +
            "SET A.state = #{reserve_cancle}, B.progressType = #{pay_cancle}\n" +
            "where A.startTime <= utc_timestamp() and B.progressType = 0")
    void deleteReservationAndPayment(@Param("reserve_cancle") final StateType stateType, @Param("pay_cancle") final ProgressType progressType);

    //결제 목록 취소 처리
    @Update("UPDATE payment SET progressType = #{progress} WHERE reserveIdx = #{reserveIdx}")
    void updatePayment(@Param("reserveIdx") final long reserveIdx, @Param("progress") final ProgressType progressType);

    ///delete에 쓰이는 쿼리
    ///////////////////////////////////////////////

    //예약 조회용 쿼리
    ///////////////////////////////////////////
    @Select("SELECT * FROM reserve WHERE reserveCode = #{reserveCode} AND storeIdx = #{storeIdx} AND state < 3")
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
    Reserve findReserveByReserveCode(@Param("reserveCode") final String reserveCode, @Param("storeIdx") final Long storeIdx);


    //특정 상태의 예약 목록만 가져오기 limit개수만
    @Select("SELECT * FROM reserve WHERE storeIdx = #{storeIdx} AND state = #{stateType} ORDER BY reserveIdx DESC limit 5")
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
    List<Reserve> findReserveByStoreIdxAndStateType(@Param("storeIdx") final Long storeIdx, @Param("stateType") final StateType stateType);

    //특정 상태의 예약 목록만 추가로 가져오기 limit개수만
    @Select("SELECT * FROM reserve WHERE storeIdx = #{storeIdx} AND state = #{stateType} AND reserveIdx < #{reserveIdx} ORDER BY reserveIdx DESC limit 5")
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
    List<Reserve> findMoreReserveByStoreIdxAndStateType(@Param("storeIdx") final Long storeIdx, @Param("stateType") final StateType stateType, @Param("reserveIdx") final Long reserveIdx);

    @Select("SELECT * FROM reserve WHERE reserveIdx = #{reserveIdx}")
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
    Reserve findReserveByReserveIdx(@Param("reserveIdx") final Long reserveIdx);

    @Select("SELECT * FROM reserve WHERE state < 3 AND storeIdx = #{storeIdx}")
    List<Reserve> findUnderPickupReserveByStoreIdx();

    @Select("SELECT COUNT(*) FROM reserve WHERE userIdx = #{userIdx} AND state < 3")
    int findReserveCntByuserIdx(@Param("userIdx")final Long userIdx);
}
