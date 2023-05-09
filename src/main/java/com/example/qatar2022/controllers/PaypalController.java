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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Controller
public class PaypalController {

    @Autowired
    PaypalService service;

    @Autowired
    ReservationService reservationService;

    @Autowired
    TicketService ticketService;

    @Autowired
    private JavaMailSender emailSender;


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
            return "reservation/error";

        } catch (PayPalRESTException e) {

            e.printStackTrace();   //ho giusto modificato sopra e ora dovre rinizializzare e provare se funzionare
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "reservation/cancel";
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
                ticketService.addTicket(ticket);


                model.addAttribute("ticket",ticket);

                model.addAttribute("paymentId", paymentId);
                model.addAttribute("payerId", payerId);
                model.addAttribute("amount", payment.getTransactions().get(0).getAmount().getTotal());
                model.addAttribute("currency", payment.getTransactions().get(0).getAmount().getCurrency());
                return "ticket/ticketDetail";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }


    @PostMapping("/sendTicket/{codeTicket}")
    public String sendTicket(@PathVariable Long codeTicket) {
        Ticket ticket = ticketService.getTicketById(codeTicket);
        Reservation reservation = ticket.getReservation();
        try {
            // Creazione del PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Ticket for reservation " + reservation.getCodeReservation());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("User: " + ticket.getReservation().getUser().getNom());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("User: " + ticket.getReservation().getUser().getPrenom());
            contentStream.newLineAtOffset(0, -20);
           // contentStream.showText("Payment number: " + ticket.getPaymentNumber());
            contentStream.newLineAtOffset(0, -20);
            contentStream.showText("Ticket number: " + ticket.getCodeTicket());
            contentStream.endText();
            contentStream.close();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            document.close();

            // Invio della mail con il PDF come allegato
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(ticket.getReservation().getUser().getEmail());
            helper.setSubject("Ticket for reservation " + reservation.getCodeReservation());
            helper.setText("Dear " + ticket.getReservation().getUser().getNom() + ",\n\nPlease find attached your ticket for reservation " +
                    reservation.getCodeReservation() + ".\n\nThank you for choosing our service!\n\nBest regards,\nYour Ben Kheder Team");
            ByteArrayDataSource dataSource = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
            helper.addAttachment("Ticket.pdf", dataSource);
            emailSender.send(message);
            return "ticket/ticketSent";
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return "ticket/ticketNotSent";
        }
    }


}
