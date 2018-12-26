package com.travely.travely.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    private long payIdx;
    private String payType;
    private long storeIdx;
    private long totalPrice;
    private long userIdx;
    private long reserveIdx;

    @Builder
    public Payment(long payIdx, String payType, long storeIdx, long totalPrice, long userIdx, long reserveIdx) {
        this.payIdx = payIdx;
        this.payType = payType;
        this.storeIdx = storeIdx;
        this.totalPrice = totalPrice;
        this.userIdx = userIdx;
        this.reserveIdx = reserveIdx;
    }
}
