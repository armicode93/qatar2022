package com.example.qatar2022.service;


import com.example.qatar2022.entities.*;
import com.example.qatar2022.repository.EquipeRepository;
import com.example.qatar2022.repository.PartieRepository;
import com.example.qatar2022.repository.ReservationRepository;
import com.example.qatar2022.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@Service
public class PartieService {

    private final PartieRepository partieRepository;
    private final TourRepository tourRepository;
    private final ReservationRepository reservationRepository;
    private final EquipeRepository equipeRepository ;



    @Autowired
    public PartieService(PartieRepository partieRepository, TourRepository tourRepository, ReservationRepository reservationRepository, EquipeRepository equipeRepository) {
        this.partieRepository = partieRepository;
        this.tourRepository = tourRepository;

        this.reservationRepository = reservationRepository;
        this.equipeRepository = equipeRepository;
    }


    public List<Partie> getAllPartie()
    {
        List <Partie> parties = new ArrayList<>();
        partieRepository.findAll().forEach(parties::add);
        return parties;
    }


    public Partie getPartieById(Long idPartie)
    {


        return partieRepository.findById(idPartie).orElse(null);
    }
    @Transactional
    public Partie getPartieByIdPartie(String idPartie)
    {

        int indice = Integer.parseInt(idPartie);
        return partieRepository.findPartieByIdPartie(indice);
    }

    public void addPartie(Partie partie)
    {

        partieRepository.save(partie);
    }
// give us False if we dont have reservation, true if we  reservation
    public boolean hasReservationsForPartie(Long idPartie) {
        Partie partie = partieRepository.findPartieByIdPartie(idPartie);
        List<Reservation> reservations = reservationRepository.findByPartie(partie);
        return !reservations.isEmpty();
    }

    public void deletePartie(Long idPartie)
    {

        partieRepository.deleteById(idPartie);
    }


    public void updatePartie(long idPartie, int scoreEq1, int scoreEq2,String prolongation,String totalTime) {
        Partie partie = getPartieById(idPartie);
        partie.setScoreEq1(scoreEq1);
        partie.setScoreEq2(scoreEq2);
        partie.setProlongation(prolongation);
        partie.setTotalTime(totalTime);
        partieRepository.save(partie);
    }

    public void editPartie(long idPartie, Stade stade, LocalDateTime dateTime, String arbitre_principal, BigDecimal prix) {
        Partie partie = getPartieById(idPartie);


        partie.setStade(stade);


        partie.setDateTime(dateTime);
        partie.setArbitre_principal(arbitre_principal);


        partie.setPrix(prix);
        partieRepository.save(partie);
    }

    @Transactional
    public void creaPartitaSuccessiva(Tour tour) {
        List<Partie> parties = partieRepository.findByTour(tour);
        boolean tutteCompletate = true;
        for (Partie partie : parties) {
            if (partie.getScoreEq1() == null || partie.getScoreEq2() == null) {
                tutteCompletate = false;
                break;
            }
        }
        if (tutteCompletate) {
            // Recupera i vincitori delle partite del girone corrente
            List<Equipe> vincitori = new ArrayList<>();
            for (Partie partie : parties) {
                if (partie.getScoreEq1() > partie.getScoreEq2()) {
                    vincitori.add(partie.getEq1());
                } else {
                    vincitori.add(partie.getEq2());
                }
            }
            // Crea le partite successive per il girone successivo
            Tour gironeSuccessivo = calcolaGironeSuccessivo(tour);
            int numPartiteSuccessive = calcolaNumPartiteSuccessive(tour);
            for (int i = 0; i <numPartiteSuccessive; i++) {
                Partie partitaSuccessiva = new Partie();
                partitaSuccessiva.setTour(gironeSuccessivo);
                partitaSuccessiva.setEq1(vincitori.get(i * 2));
                partitaSuccessiva.setEq2(vincitori.get(i * 2 + 1));
                partieRepository.save(partitaSuccessiva);
            }
        }
    }




    private Tour calcolaGironeSuccessivo(Tour gironeCorrente) {
        String nomTour = gironeCorrente.getNomTour();
        switch(nomTour) {
            case "EIGHTS":
                return new Tour("FOURTHS");
            case "FOURTHS":
                return new Tour("SEMIFINAL");
            case "SEMIFINAL":
                return new Tour("FINAL");
            default:
                // gestisci il caso in cui il girone corrente non sia valido
                throw new IllegalArgumentException("Girone corrente non valido: " + nomTour);
        }
    }


    private int calcolaNumPartiteSuccessive(Tour gironeCorrente) {
        String nomTour = gironeCorrente.getNomTour();
        switch(nomTour) {
            case "EIGHTS":
                return 4; // ci sono 4 partite nei quarti di finale
            case "FOURTHS":
                return 2; // ci sono 2 partite nelle semifinali
            case "SEMIFINAL":
                return 1; // c'è 1 partita nella finale
            default:
                // gestisci il caso in cui il girone corrente non sia valido
                throw new IllegalArgumentException("Girone corrente non valido: " + nomTour);
        }
    }


public void createMatches() {
    List<Equipe> teams = equipeRepository.findAll();
    if (teams.size() >= 4) {
        Tour tour = new Tour("Ottavi di finale");
        createMatch(teams.get(0), teams.get(1), tour);
        createMatch(teams.get(0), teams.get(2), tour);
        createMatch(teams.get(0), teams.get(3), tour);
        createMatch(teams.get(1), teams.get(2), tour);
        createMatch(teams.get(1), teams.get(3), tour);
        createMatch(teams.get(2), teams.get(3), tour);
    } else {

    }
}

    public Partie createMatch(Equipe team1, Equipe team2, Tour tour) {
        Partie match = new Partie();
        match.setEq1(team1);
        match.setEq2(team2);
        match.setTour(tour);

        return partieRepository.save(match);
    }

    public LocalDateTime convertStringToLocalDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }
    public String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dateTime.format(formatter);
    }

}
