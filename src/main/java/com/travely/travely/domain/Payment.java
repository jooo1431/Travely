package com.travely.travely.domain;

import com.travely.travely.util.typeHandler.PayType;
import com.travely.travely.util.typeHandler.ProgressType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    private long payIdx;
    private PayType payType;
    private long totalPrice;
    private long reserveIdx;
    private ProgressType progressType;

    @Builder
    public Payment(long payIdx, PayType payType,long totalPrice, long reserveIdx, ProgressType progressType) {
        this.payIdx = payIdx;
        this.payType = payType;
        this.totalPrice = totalPrice;
        this.reserveIdx = reserveIdx;
        this.progressType=progressType;
    }
}
