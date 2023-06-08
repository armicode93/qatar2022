package com.example.qatar2022.controllers;

import com.example.qatar2022.config.Order;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Reservation;
import com.example.qatar2022.entities.Stade;
import com.example.qatar2022.entities.Ticket;
import com.example.qatar2022.service.PaypalService;
import com.example.qatar2022.service.ReservationService;
import com.example.qatar2022.service.StadeService;
import com.example.qatar2022.service.TicketService;
import com.example.qatar2022.utils.MethodUtils;
import com.paypal.api.payments.Links;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;


import java.util.List;


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

    @Autowired
    private StadeService stadeService;

    private final String data = "";

    //where do you want to generate the QR code

    private final String imagePath = "./src/main/resources/static/images/QRCode.png";


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
                int numberOfTickets= reservation.getNbr_places();
                List<Ticket> tickets = new ArrayList<>();

                for(int i = 0; i < numberOfTickets;i++)
                {
                    Ticket ticket = new Ticket();
                    ticket.setReservation(reservation);

                    //QrCode


                    ticketService.addTicket(ticket);

                    tickets.add(ticket); // adding ticket into the List
                }

                //to add the codeTicket into the qrcode
                for (Ticket ticket : tickets) {
                    // Genera il dettaglio del biglietto utilizzando il codice del biglietto e altre informazioni
                    String ticketDetails = "Code Ticket: " + ticket.getCodeTicket() +
                            "\nReservation ID: " + ticket.getReservation().getCodeReservation() +
                            "\nPayment ID: " + paymentId +
                            "\nPayer ID: " + payerId +
                            "\nPayment Method: PayPal" +
                            "\nNumber of Ticket: " + ticket.getReservation().getNbr_places() +
                            "\nAmount: " + ticket.getReservation().getPartie().getPrix() +
                            "\nStadium: " + ticket.getReservation().getPartie().getStade().getNomStade() +
                            "\nDate Time: " + ticket.getReservation().getPartie().getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                    byte[] byteQRCode = MethodUtils.generateByteQRCode(ticketDetails, 250, 250, imagePath);

                    ticket.setBarCode(byteQRCode);
                }




                //Refresh stadium capacity
                Partie partie = reservation.getPartie();
                Stade stade = partie.getStade();

                //take the current capacity of the stadium
                Long currentCapacity = stade.getCapacite();

                // subtract nbr_place selected with actualCapacity
                currentCapacity = currentCapacity- reservation.getNbr_places();

                stade.setCapacite(currentCapacity);
                stadeService.updateStade(stade.getIdStade(),stade);

                //Generated QRcode

                //MethodUtils.generateImageQRCode(partie.getEq1().getPays(),250,250,imagePath);

                model.addAttribute("tickets",tickets);
                model.addAttribute("stade",stade);
                model.addAttribute("reservation",reservation);
                model.addAttribute("paymentId", paymentId);
                model.addAttribute("payerId", payerId);
                model.addAttribute("amount", payment.getTransactions().get(0).getAmount().getTotal());
                model.addAttribute("currency", payment.getTransactions().get(0).getAmount().getCurrency());
                return "ticket/ticketDetail";
            }
            else {
                // Payment not approved
                Reservation reservation = reservationService.getReservationById(idReservation);
                reservation.setPaye(false);
                reservationService.updateReservation(reservation);



                return "redirect:/reservation/cancel";
            }
        }



        catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }





    @PostMapping("/sendTicket/{codeReservation}")
    public String sendTicket(@PathVariable Long codeReservation) {
        //Ticket ticket = ticketService.getTicketById(codeTicket);
        Reservation reservation = reservationService.getReservationById(codeReservation);
        List <Ticket> tickets = ticketService.getTicketsByReservation(reservation);
        try {
            // Creazione del PDF
            PDDocument document = new PDDocument();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            for(Ticket t : tickets) {

                PDPage page = new PDPage();
                document.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Ticket for reservation: " + reservation.getCodeReservation());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("FirstName: " + t.getReservation().getUser().getNom());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("LastName: " + t.getReservation().getUser().getPrenom());
                contentStream.newLineAtOffset(0, -20);
                // contentStream.showText("Payment number: " + ticket.getPaymentNumber());

                contentStream.showText("Ticket number: " + t.getCodeTicket());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Amount: " + t.getReservation().getPartie().getPrix() + " € ");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Match: " + t.getReservation().getPartie().getEq1().getPays() + " vs " + t.getReservation().getPartie().getEq2().getPays());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Date: " + t.getReservation().getPartie().getDateTimeAsString());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Stadium: " + t.getReservation().getPartie().getStade().getNomStade());


                contentStream.newLineAtOffset(0, -20);

                contentStream.endText();


                byte[] qrCodeBytes = t.getBarCode(); // Assuming the QR code bytes are in PNG format

                //here i will create a new object pdimagexobject , this object will rapresent the qrcode image into the pdf document
                //i use this object because he is the one in charge of the insertionof a pictures into a pdf
                // i use the method createFromByteArray  with this method i can create an object  from a byte array
                PDImageXObject qrCodeImage = PDImageXObject.createFromByteArray(document, qrCodeBytes, "png");

                //instane of pdpagecontentstream, i use it to draw into a pdf file
                contentStream.drawImage(qrCodeImage, 50, 50);

                contentStream.close();

                // Generazione e inserimento del codice QR nel PDF





                document.save(baos);    // i use baos to save the document

            }

            document.close();
            // Generate a QR code image for each ticket




            // Invio della mail con il PDF come allegato
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(reservation.getUser().getEmail());
            helper.setSubject("Ticket for reservation " + reservation.getCodeReservation());
            helper.setText("Dear " + reservation.getUser().getNom() + ",\n\nPlease find attached your ticket for reservation " +
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

    //to show the qrcode
    @GetMapping("/qrcode/{codeTicket}")
    void  getQRCodeImage(@PathVariable("codeTicket") Long codeTicket, HttpServletResponse response) throws IOException {
        Ticket ticket = ticketService.getTicketById(codeTicket);
        response.setContentType("qrCode/png");
        response.getOutputStream().write(ticket.getBarCode());
        response.getOutputStream().close();

    }


}
