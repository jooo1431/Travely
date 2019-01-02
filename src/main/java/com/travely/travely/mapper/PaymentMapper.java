package com.travely.travely.mapper;

import com.travely.travely.domain.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PaymentMapper {

    @Select("SELECT * FROM payment WHERE reserveIdx = #{reserveIdx}")
    Payment findPaymentByReserveIdx(@Param("reserveIdx") final Long reserveIdx);
}
