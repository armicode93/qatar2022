package com.example.qatar2022.service;

import com.example.qatar2022.entities.*;
import com.example.qatar2022.repository.EquipeRepository;
import com.example.qatar2022.repository.PartieRepository;
import com.example.qatar2022.repository.ReservationRepository;
import com.example.qatar2022.repository.TourRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartieService {

  private final PartieRepository partieRepository;
  private final TourRepository tourRepository;
  private final ReservationRepository reservationRepository;
  private final EquipeRepository equipeRepository;

  @Autowired
  public PartieService(
      PartieRepository partieRepository,
      TourRepository tourRepository,
      ReservationRepository reservationRepository,
      EquipeRepository equipeRepository) {
    this.partieRepository = partieRepository;
    this.tourRepository = tourRepository;

    this.reservationRepository = reservationRepository;
    this.equipeRepository = equipeRepository;
  }

  public List<Partie> getAllPartie() {
    List<Partie> parties = new ArrayList<>();
    partieRepository.findAll().forEach(parties::add);
    return parties;
  }

  public Partie getPartieById(Long idPartie) {

    return partieRepository.findById(idPartie).orElse(null);
  }

  @Transactional
  public Partie getPartieByIdPartie(String idPartie) {

    int indice = Integer.parseInt(idPartie);
    return partieRepository.findPartieByIdPartie(indice);
  }

  public void addPartie(Partie partie) {

    partieRepository.save(partie);
  }
  // give us False if we dont have reservation, true if we  reservation
  public boolean hasReservationsForPartie(Long idPartie) {
    Partie partie = partieRepository.findPartieByIdPartie(idPartie);
    List<Reservation> reservations = reservationRepository.findByPartie(partie);
    return !reservations.isEmpty();
  }

  public void deletePartie(Long idPartie) {

    partieRepository.deleteById(idPartie);
  }

  public void updatePartie(
      long idPartie, int scoreEq1, int scoreEq2, String prolongation, String totalTime) {
    Partie partie = getPartieById(idPartie);
    partie.setScoreEq1(scoreEq1);
    partie.setScoreEq2(scoreEq2);
    partie.setProlongation(prolongation);
    partie.setTotalTime(totalTime);
    partieRepository.save(partie);
  }

  public void editPartie(
      long idPartie,
      Stade stade,
      LocalDateTime dateTime,
      String arbitre_principal,
      BigDecimal prix) {
    Partie partie = getPartieById(idPartie);

    partie.setStade(stade);

    partie.setDateTime(dateTime);
    partie.setArbitre_principal(arbitre_principal);

    partie.setPrix(prix);
    partieRepository.save(partie);
  }
  //ok
  @Transactional //one note see
  public void createInitialKnockoutMatches(List<Equipe> equipes) {

    BigDecimal defaultPrix = new BigDecimal("50.00");
    LocalDateTime currentDateTime = LocalDateTime.now().plusDays(1);

    Tour tour = new Tour("HUITIEME_DE_FINALE");
    for (int i = 0; i < 8; i++) {
      Partie match = new Partie();
      match.setEq1(equipes.get(i * 2));
      match.setEq2(equipes.get(i * 2 + 1));

      match.setPrix(defaultPrix);
      match.setDateTime(currentDateTime);
      match.setTour(tour);

      partieRepository.save(match);
    }
  }
//
  @Transactional
  public void createQuarts(List<Equipe> currentTourWinners) {

   // List<Equipe> quarterFinalWinners = calculateGroupWinners(parties,"HUITIEME_DE_FINALE");

    Tour tour = new Tour("QUARTS_DE_FINALE");
    BigDecimal defaultPrix = new BigDecimal("50.00");
    LocalDateTime currentDateTime = LocalDateTime.now().plusDays(2);




    for (int i = 0; i < 4; i++) {

      Partie match = new Partie();
      match.setEq1(currentTourWinners.get(i * 2));
      match.setEq2(currentTourWinners.get(i * 2 + 1));
      match.setPrix(defaultPrix);
      match.setDateTime(currentDateTime);
      match.setTour(tour);

      partieRepository.save(match);
    }
  }


  @Transactional
  public void createDemiFinal(List<Equipe> currentTourWinners) {

   // List<Equipe> demiFinalWinners = calculateGroupWinners(parties,"QUARTS_DE_FINALE");

    Tour tour = new Tour("DEMI_FINAL");
    BigDecimal defaultPrix = new BigDecimal("50.00");
    LocalDateTime currentDateTime = LocalDateTime.now().plusDays(2);


    for (int i = 0; i < 2; i++) {
      Partie match = new Partie();
      match.setEq1(currentTourWinners.get(i * 2));
      match.setEq2(currentTourWinners.get(i * 2 + 1));
      match.setPrix(defaultPrix);
      match.setDateTime(currentDateTime);
      match.setTour(tour);

      partieRepository.save(match);
    }
  }

  @Transactional
  public void createFinal(List<Equipe> currentTourWinners) {

   // List<Equipe> finalWinners = calculateGroupWinners(parties,"DEMI_FINAL");

    Tour tour = new Tour("FINAL");
    BigDecimal defaultPrix = new BigDecimal("50.00");
    LocalDateTime currentDateTime = LocalDateTime.now().plusDays(2);


    Partie finaleMatch = new Partie();
    finaleMatch.setEq1(currentTourWinners.get(0));
    finaleMatch.setEq2(currentTourWinners.get(1));
    finaleMatch.setPrix(defaultPrix);
    finaleMatch.setDateTime(currentDateTime);
    finaleMatch.setTour(tour);

    partieRepository.save(finaleMatch);

  }
  public List<Equipe> calculateGroupWinners(List<Partie> parties, String prochainTourName) {
    Tour currentTour = null;
    List<Equipe> groupWinners = new ArrayList<>();

    if(prochainTourName.equals("QUARTS_DE_FINALE"))
    {
      currentTour = tourRepository.findByNomTour("HUITIEME_DE_FINALE");
    } else if (prochainTourName.equals("DEMI_FINAL")) {

      currentTour = tourRepository.findByNomTour("QUARTS_DE_FINALE");
    }
    else if (prochainTourName.equals("FINAL")) {

      currentTour = tourRepository.findByNomTour("DEMI_FINAL");
    }


    List<Partie> currentTourParties = partieRepository.findByTour(currentTour);

    for (int i = 0; i < currentTourParties.size(); i++) {
      Partie partieTour = currentTourParties.get(i);
      Equipe winner = getWinner(partieTour);

      if (winner != null) {
        System.out.println("Added winner: " + winner);
        groupWinners.add(winner);
      } else {
        System.out.println("No winner for partie: " + parties);
      }
    }




/*
    if (groupWinners.isEmpty()) {
      throw new RuntimeException("groupwinnerisEmpty");
    }

 */

    return groupWinners;
  }
  public Equipe getWinner(Partie partie) {

    int scoreEq1 = partie.getScoreEq1();
    int scoreEq2 = partie.getScoreEq2();

    if (scoreEq1 < 0 || scoreEq2 < 0) {
      throw new RuntimeException("Invalid scores: " + scoreEq1 + ", " + scoreEq2);
    }

    if (scoreEq1 > scoreEq2) {
      return partie.getEq1();
    } else if (scoreEq2 > scoreEq1) {
      return partie.getEq2();
    }


    return null;


  }

  public LocalDateTime convertStringToLocalDateTime(String dateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    return LocalDateTime.parse(dateTime, formatter);
  }

  public String formatDateTime(LocalDateTime dateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    return dateTime.format(formatter);
  }

  public Equipe getEq1ByPartieId(Long idPartie) {
    Partie partie = partieRepository.findById(idPartie).orElse(null);
    if (partie != null) {
      return partie.getEq1();
    }
    return null;
  }

  public Equipe getEq2ByPartieId(Long idPartie) {
    Partie partie = partieRepository.findById(idPartie).orElse(null);
    if (partie != null) {
      return partie.getEq2();
    }
    return null;
  }
}
