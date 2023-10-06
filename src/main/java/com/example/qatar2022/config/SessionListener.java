package com.example.qatar2022.config;

import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.service.ReservationService;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    private final ReservationService reservationService;

    public SessionListener(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        HttpSession session = se.getSession();
        Reservation reservation = (Reservation) session.getAttribute("reservation");

        if (reservation != null) {
            reservationService.deleteReservation(reservation.getCodeReservation());
            session.removeAttribute("reservation");
        }
    }
}
