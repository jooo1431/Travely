package com.travely.travely.mapper;

import com.travely.travely.domain.Payment;
import com.travely.travely.util.typeHandler.ProgressType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PaymentMapper {

    @Select("SELECT * FROM payment WHERE reserveIdx = #{reserveIdx}")
    Payment findPaymentByReserveIdx(@Param("reserveIdx") final Long reserveIdx);

    @Update("UPDATE payment SET progressType = #{progressType} WHERE payIdx = #{payIdx}")
    void updatePaymentByPayIdx(@Param("payIdx") final Long payIdx, @Param("progressType") final ProgressType progressType);
}
