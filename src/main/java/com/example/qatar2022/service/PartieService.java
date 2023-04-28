package com.example.qatar2022.service;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Stade;
import com.example.qatar2022.entities.Tour;
import com.example.qatar2022.repository.PartieRepository;
import com.example.qatar2022.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@Service
public class PartieService {

    private final PartieRepository partieRepository;
    private final TourRepository tourRepository;



    @Autowired
    public PartieService(PartieRepository partieRepository, TourRepository tourRepository) {
        this.partieRepository = partieRepository;
        this.tourRepository = tourRepository;

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

    public void deletePartie(Long idPartie)
    {
        boolean exists = partieRepository.existsById(idPartie);
        if(!exists)
        {
            throw new IllegalStateException("Not exists");
        }
        partieRepository.deleteById(idPartie);
    }

    /*
    public void updatePartie(Long idPartie, Partie partie)
    {
        partieRepository.save(partie);

    }

     */
    public void updatePartie(long idPartie, int scoreEq1, int scoreEq2) {
        Partie partie = getPartieById(idPartie);
        partie.setScoreEq1(scoreEq1);
        partie.setScoreEq2(scoreEq2);
        partieRepository.save(partie);
    }

    public void editPartie(long idPartie, Partie partie) {

        partie.setEq1(partie.getEq1());
        partie.setEq2(partie.getEq2());
        partie.setStade(partie.getStade());

        partie.setTour(partie.getTour());
        partie.setDateTime(partie.getDateTime());
        partie.setArbitre_principal(partie.getArbitre_principal());
        partie.setTotalTime(partie.getTotalTime());
        partie.setProlongation(partie.getProlongation());
        partie.setPrix(partie.getPrix());
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

}
