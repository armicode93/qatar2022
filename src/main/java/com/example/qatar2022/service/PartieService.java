package com.example.qatar2022.service;


import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Tour;
import com.example.qatar2022.repository.PartieRepository;
import com.example.qatar2022.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartieService {

    private final PartieRepository partieRepository;
    private final TourRepository TourRepository;



    @Autowired
    public PartieService(PartieRepository partieRepository, com.example.qatar2022.repository.TourRepository tourRepository) {
        this.partieRepository = partieRepository;
        TourRepository = tourRepository;
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

    /*
    public List<Partie> getPartiteByTurno(Tour tour) {

        List <Partie> parties = new ArrayList<>();
        partieRepository.findAllOrderByTour(tour).forEach(parties::add);
        return parties;


    }

     */
}
