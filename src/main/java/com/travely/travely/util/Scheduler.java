package com.travely.travely.util;


import com.travely.travely.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ReservationService reservationService;

    @Scheduled(cron = "0 0/5 * * * *")
    public void updatePendingOrderHistory() {
        reservationService.cancelReservation();
    }
}