package com.example.qatar2022.service;

import com.example.qatar2022.entities.Tour;
import com.example.qatar2022.repository.PartieRepository;
import com.example.qatar2022.repository.TourRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {

  private final TourRepository tourRepository;
  private final PartieRepository partieRepository;

  @Autowired
  public TourService(TourRepository tourRepository, PartieRepository partieRepository) {
    this.tourRepository = tourRepository;
    this.partieRepository = partieRepository;
  }

  public List<Tour> getallTour() {
    List<Tour> tours = new ArrayList<>();

    tourRepository.findAll().forEach(tours::add);
    return tours;
  }

  public Tour getTourById(Long idTour) {
    return tourRepository.findById(idTour).orElse(null);
  }

  public void addTour(Tour tour) {
    tourRepository.save(tour);
  }

  public void deleteTour(Long idTour) {
    boolean exists = tourRepository.existsById(idTour);
    if (!exists) {
      throw new IllegalStateException("Not exists");
    }
    tourRepository.deleteById(idTour);
  }

  public Tour updateTour(Long idTour, Tour tour) {
    tourRepository.save(tour);
    return tour;
  }
}
