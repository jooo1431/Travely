package com.travely.travely.dto.reservation;

import com.travely.travely.util.typeHandler.PayType;
import com.travely.travely.util.typeHandler.ProgressType;
import com.travely.travely.util.typeHandler.StateType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ReserveJoinPayment {
    private long reserveIdx;
    private long userIdx;
    private long storeIdx;
    private Timestamp startTime;
    private Timestamp endTime;
    private StateType stateType;
    private long price;
    private String reserveCode;
    private Timestamp depositTime;
    private Timestamp takeTime;
    private long payIdx;
    private PayType payType;
    private long totalPrice;
    private ProgressType progressType;

    @Builder
    public ReserveJoinPayment(long reserveIdx, long userIdx, long storeIdx, Timestamp startTime, Timestamp endTime, StateType stateType, long price, String reserveCode, Timestamp depositTime, Timestamp takeTime, long payIdx, PayType payType, long totalPrice, ProgressType progressType) {
        this.reserveIdx = reserveIdx;
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stateType = stateType;
        this.price = price;
        this.reserveCode = reserveCode;
        this.depositTime = depositTime;
        this.takeTime = takeTime;
        this.payIdx = payIdx;
        this.payType = payType;
        this.totalPrice = totalPrice;
        this.progressType = progressType;
    }
}
