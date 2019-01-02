package com.travely.travely.service;

import com.travely.travely.domain.Reserve;
import com.travely.travely.domain.Review;
import com.travely.travely.domain.Store;
import com.travely.travely.dto.owner.OwnerArchiveInfoResponseDto;
import com.travely.travely.dto.owner.OwnerArchiveResponseDto;
import com.travely.travely.dto.review.ReviewUserImgResponseDto;
import com.travely.travely.exception.AuthenticationErrorException;
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

    final private StateType ARCHIVE = StateType.Archiving;
    final private StateType PICKUP = StateType.TakeOff;

    public List<OwnerArchiveResponseDto> getArchives(final Long userIdx) {
        final Store store = storeMapper.findStoreByUserIdx(userIdx);
        final List<Reserve> reserveList = reservationMapper.findReserveByStoreIdxAndStateType(store.getStoreIdx(), ARCHIVE);

        if (reserveList == null) throw new NotFoundReserveException();

        List<OwnerArchiveResponseDto> ownerArchiveResponseDtos = reserveList.stream().map(reserve -> new OwnerArchiveResponseDto(reserve)).collect(Collectors.toList());
        return ownerArchiveResponseDtos;
    }

    public List<OwnerArchiveResponseDto> getMoreArchives(final Long userIdx, final Long reserveIdx) {
        final Store store = storeMapper.findStoreByUserIdx(userIdx);
        final List<Reserve> reserveList = reservationMapper.findMoreReserveByStoreIdxAndStateType(store.getStoreIdx(), ARCHIVE, reserveIdx);

        if (reserveList == null) throw new NotFoundReserveException();

        List<OwnerArchiveResponseDto> ownerArchiveResponseDtos = reserveList.stream().map(reserve -> new OwnerArchiveResponseDto(reserve)).collect(Collectors.toList());
        return ownerArchiveResponseDtos;
    }

    public OwnerArchiveInfoResponseDto getArchiveByReserveIdx(final Long ownerIdx, final Long reserveIdx) {
        final Reserve reserve = reservationMapper.findReserveByReserveIdxAndState(reserveIdx, ARCHIVE);

        if (reserve == null) throw new NotFoundReserveException();
        if (reserve.getStore().getOwnerIdx() != ownerIdx) throw new AuthenticationErrorException();

        OwnerArchiveInfoResponseDto ownerArchiveInfoResponseDto = OwnerArchiveInfoResponseDto.builder().reserve(reserve).build();
        return ownerArchiveInfoResponseDto;
    }

    public void pickUpBaggage(final Long ownerIdx, final Long reserveIdx) {
        final Reserve reserve = reservationMapper.findReserveByReserveIdxAndState(reserveIdx, ARCHIVE);

        if (reserve == null) throw new NotFoundReserveException();
        if (reserve.getStore().getOwnerIdx() != ownerIdx) throw new AuthenticationErrorException();

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
}