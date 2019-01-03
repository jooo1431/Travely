package com.travely.travely.service;

import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Review;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.owner.ReserveArchiveInfoResponseDto;
import com.travely.travely.dto.owner.ReserveArchiveResponseDto;
import com.travely.travely.dto.review.ReviewUserImgResponseDto;
import com.travely.travely.exception.AuthenticationErrorException;
import com.travely.travely.exception.NotFoundReserveArchiveException;
import com.travely.travely.exception.NotFoundReserveException;
import com.travely.travely.exception.NotFoundReviewException;
import com.travely.travely.mapper.ReservationMapper;
import com.travely.travely.mapper.ReviewMapper;
import com.travely.travely.mapper.StoreMapper;
import com.travely.travely.util.typeHandler.StateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OwnerService {

    final private StoreMapper storeMapper;
    final private ReservationMapper reservationMapper;
    final private ReviewMapper reviewMapper;

    final private StateType RESERVED = StateType.RESERVED;
    final private StateType PAYED = StateType.PAYED;
    final private StateType ARCHIVE = StateType.ARCHIVE;
    final private StateType PICKUP = StateType.PICKUP;


    public ReserveArchiveInfoResponseDto getArchiveByReserveIdx(final Long ownerIdx, final Long reserveIdx) {
        final Reserve reserve = reservationMapper.findReserveByReserveIdx(reserveIdx);

        if (reserve == null) throw new NotFoundReserveException();
        if (reserve.getStore().getOwnerIdx() != ownerIdx) throw new AuthenticationErrorException();

        ReserveArchiveInfoResponseDto reserveArchiveInfoResponseDto = new ReserveArchiveInfoResponseDto(reserve);
        return reserveArchiveInfoResponseDto;
    }

    public void updateBaggageState(final Long ownerIdx, final Long reserveIdx) {
        final Reserve reserve = reservationMapper.findReserveByReserveIdx(reserveIdx);

        if (reserve == null) throw new NotFoundReserveException();
        if (reserve.getStore().getOwnerIdx() != ownerIdx) throw new AuthenticationErrorException();

        if (reserve.getState() == RESERVED && reserve.getState() == PAYED)
            reservationMapper.updateReservation(reserve.getReserveIdx(), ARCHIVE);
        else if (reserve.getState() == ARCHIVE)
            reservationMapper.updateReservation(reserve.getReserveIdx(), PICKUP);
    }

    public List<ReviewUserImgResponseDto> getReviews(final Long ownerIdx) {
        Store store = storeMapper.findStoreByUserIdx(ownerIdx);
        List<Review> reviewList = reviewMapper.findReviewsByStoreIdx(store.getStoreIdx());

        if (reviewList == null) throw new NotFoundReviewException();

        List<ReviewUserImgResponseDto> reviewUserImgResponseDtos = reviewList.stream().map(review -> new ReviewUserImgResponseDto(review)).collect(Collectors.toList());
        return reviewUserImgResponseDtos;
    }

    public List<ReviewUserImgResponseDto> getMoreReviews(final Long ownerIdx, final Long reviewIdx) {
        Store store = storeMapper.findStoreByUserIdx(ownerIdx);
        List<Review> reviewList = reviewMapper.findMoreReviewsByStoreIdx(store.getStoreIdx(), reviewIdx);

        if (reviewList == null) throw new NotFoundReviewException();

        List<ReviewUserImgResponseDto> reviewUserImgResponseDtos = reviewList.stream().map(review -> new ReviewUserImgResponseDto(review)).collect(Collectors.toList());
        return reviewUserImgResponseDtos;
    }

    public List<ReserveArchiveResponseDto> getReservedAndArchiving(final Long ownerIdx){
        Store store = storeMapper.findStoreByUserIdx(ownerIdx);
        List<Reserve> reserveList = reservationMapper.findReserveByStoreIdx(store.getStoreIdx());
        if(reserveList ==null) throw new NotFoundReserveArchiveException();
        List<ReserveArchiveResponseDto> reserveArchiveResponseDtos = reserveList.stream().map(reserve -> new ReserveArchiveResponseDto(reserve)).collect(Collectors.toList());
        return reserveArchiveResponseDtos;
    }

    public ReserveArchiveInfoResponseDto readReserveCode(final Long ownerIdx, final String reserveCode){
        Reserve reserve = reservationMapper.findReserveByReserveCode(reserveCode);
        if(reserve == null) throw new NotFoundReserveException();
        if(ownerIdx != reserve.getStore().getOwnerIdx()) throw new AuthenticationErrorException();
        ReserveArchiveInfoResponseDto reserveArchiveInfoResponseDto = new ReserveArchiveInfoResponseDto(reserve);
        return reserveArchiveInfoResponseDto;
    }

}