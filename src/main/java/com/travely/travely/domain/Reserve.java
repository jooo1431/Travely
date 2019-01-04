package com.travely.travely.domain;

import com.travely.travely.config.CommonConfig;
import com.travely.travely.exception.AlreadyExistsReserveException;
import com.travely.travely.exception.NotFoundPaymentException;
import com.travely.travely.exception.NotFoundStoreException;
import com.travely.travely.exception.NotFoundUserException;
import com.travely.travely.util.typeHandler.StateType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reserve {
    private Long reserveIdx;
    private Long userIdx;
    private Long storeIdx;
    private Timestamp startTime;
    private Timestamp endTime;
    private StateType state;
    private Long price;
    private String reserveCode;
    private Timestamp depositTime;
    private Timestamp takeTime;

    private List<Baggage> baggages;
    private List<BaggageImg> baggageImgs;
    private Payment payment;
    private Store store;
    private Users users;

    @Builder
    public Reserve(long reserveIdx, long userIdx, long storeIdx, Timestamp startTime, Timestamp endTime, StateType state, long price, String reserveCode, Timestamp depositTime, Timestamp takeTime, List<Baggage> baggages, List<BaggageImg> baggageImgs, Payment payment) {
        this.reserveIdx = reserveIdx;
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.price = price;
        this.reserveCode = reserveCode;
        this.depositTime = depositTime;
        this.takeTime = takeTime;
        this.baggages = baggages;
        this.baggageImgs = baggageImgs;
        this.payment = payment;
    }

    public Timestamp getDepositTime() {
        if (depositTime == null) return new Timestamp(0);
        return depositTime;
    }

    public Timestamp getTakeTime() {
        if (takeTime == null) return new Timestamp(0);
        return takeTime;
    }

    public List<Baggage> getBaggages() {
        return CommonConfig.getCheckedList(baggages);
    }

    public List<BaggageImg> getBaggageImgs() {
        return CommonConfig.getCheckedList(baggageImgs);
    }

    public Payment getPayment() {
        if (payment == null) throw new NotFoundPaymentException();
        return payment;
    }

    public Store getStore() {
        if (store == null) throw new NotFoundStoreException();
        return store;
    }

    public Users getUsers() {
        if (this.users == null) throw new NotFoundUserException();
        return this.users;
    }

    public Long getBagCount() {
        if (state.checkReserve()) {
            List<Baggage> baggages = CommonConfig.getCheckedList(this.baggages);
            return baggages.stream().map(Baggage::getBagCount).reduce((x, y) -> x + y).orElse(0L);
        }
        return 0L;
    }

    public Long getTotalBag() {
        return baggages.stream().mapToLong(Baggage::getBagCount).sum();
    }

    public void checkReserved(final Long userIdx) {
        if (this.userIdx == userIdx) throw new AlreadyExistsReserveException();
    }

    @Builder
    public Reserve(long userIdx, long storeIdx, Timestamp startTime, Timestamp endTime, StateType state, long price, String reserveCode, Timestamp depositTime, Timestamp takeTime, List<Baggage> baggages, List<BaggageImg> baggageImgs, Payment payment) {
        this.userIdx = userIdx;
        this.storeIdx = storeIdx;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.price = price;
        this.reserveCode = reserveCode;
        this.depositTime = depositTime;
        this.takeTime = takeTime;
        this.baggages = baggages;
        this.baggageImgs = baggageImgs;
        this.payment = payment;
    }

}
