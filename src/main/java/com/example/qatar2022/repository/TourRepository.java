package com.example.qatar2022.repository;

import com.example.qatar2022.entities.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

  Tour findByNomTour(String nomTour);
}
