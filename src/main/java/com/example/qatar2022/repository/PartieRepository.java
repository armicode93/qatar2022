package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Tour;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartieRepository extends JpaRepository<Partie, Long> {

  // List<Partie> findAllByTour(Tour tour);

  Partie findPartieByIdPartie(long idPartie);

  List<Partie> findByTour(Tour tour);

  // List<Partie>findByTourOrOrderByScoreEq1DescScoreEq2Desc(Tour tour);

}
