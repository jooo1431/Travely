package com.travely.travely.mapper;

import com.travely.travely.domain.Payment;
import com.travely.travely.util.typeHandler.ProgressType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PaymentMapper {

    // ReservationMapper List<Reserve> findUnderArvhiceReserveByOwnerIdx(@Param("ownerIdx") final Long ownerIdx); 에서 사용중
    @Select("SELECT * FROM payment WHERE reserveIdx = #{reserveIdx}")
    Payment findPaymentByReserveIdx(@Param("reserveIdx") final Long reserveIdx);
}
