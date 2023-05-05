package com.example.qatar2022.controllers;

import com.example.qatar2022.config.Order;
import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.entities.Ticket;
import com.example.qatar2022.service.PaypalService;
import com.example.qatar2022.service.ReservationService;
import com.example.qatar2022.service.TicketService;
import com.paypal.api.payments.Links;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PaypalController {

    @Autowired
    PaypalService service;

    @Autowired
    ReservationService reservationService;

    @Autowired
    TicketService ticketService;

    public static final String SUCCESS_URL = "pay/success/";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/pay/{idReservation}")
    public String home(@PathVariable("idReservation") Long idReservation, Model model)
    {
        Reservation reservation= reservationService.getReservationById(idReservation);

        model.addAttribute("reservation",reservation);
        return "reservation/paypalPayment";
    }

    @PostMapping("/pay/{idReservation}")
    public String payment(@PathVariable("idReservation") Long idReservation,@ModelAttribute("order") Order order) {
        try {
            //create payment object
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:8080/" + CANCEL_URL,
                    "http://localhost:8080/" + SUCCESS_URL+  idReservation);
           // link form our payment object, if that link approve out url go to approval url o senno non aprobal url
             for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref(); //paypal payment procissing API
                }
            }
            // Payment is not approved
            Reservation reservation = reservationService.getReservationById(idReservation);
            // setta il valore paye a false
            reservation.setPaye(false);
            reservationService.updateReservation(reservation);
            return "error";

        } catch (PayPalRESTException e) {

            e.printStackTrace();   //ho giusto modificato sopra e ora dovre rinizializzare e provare se funzionare
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = "/pay/success/{idReservation}")
    public String successPay(@PathVariable("idReservation") Long idReservation,@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,Model model) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                Reservation reservation = reservationService.getReservationById(idReservation);
                // setta il valore paye a true
                reservation.setPaye(true);
                reservationService.updateReservation(reservation);

                //New Ticket
                Ticket ticket = new Ticket();
                ticket.setReservation(reservation);

                model.addAttribute("ticket",ticket);
                model.addAttribute("ticket", ticket);
                model.addAttribute("paymentId", paymentId);
                model.addAttribute("payerId", payerId);
                model.addAttribute("amount", payment.getTransactions().get(0).getAmount().getTotal());
                model.addAttribute("currency", payment.getTransactions().get(0).getAmount().getCurrency());
                return "ticketDetail";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}
