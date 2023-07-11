package com.example.qatar2022.controllers;

import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Tour;
import com.example.qatar2022.service.PartieService;
import com.example.qatar2022.service.TourService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tour")
@CrossOrigin(origins = "*")
public class TourController {

  private final TourService tourService;
  private final PartieService partieService;

  public TourController(TourService tourService, PartieService partieService) {
    this.tourService = tourService;
    this.partieService = partieService;
  }

  @GetMapping("/")
  public String index(Model model) {
    List<Partie> parties = partieService.getAllPartie();
    List<Tour> tours = tourService.getallTour();

    model.addAttribute("tours", tours);
    model.addAttribute("parties", parties);
    return "index";
  }

  @GetMapping("{idTour}")
  public ResponseEntity findTourById(@PathVariable(name = "idTour") Long idTour) {
    if (idTour == null) {
      return ResponseEntity.badRequest().body("Empty parametre");
    }
    Optional<Tour> tours = Optional.ofNullable(tourService.getTourById(idTour));

    if (tours.isPresent()) {
      return ResponseEntity.ok(tours);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/")
  public ResponseEntity createTour(@RequestBody Tour tourBody) {

    if (tourBody == null) {

      return ResponseEntity.badRequest().body("Empty Request Body");
    }

    Optional<Tour> tours = Optional.ofNullable(tourService.getTourById(tourBody.getIdTour()));

    if (!tours.isPresent()) {
      tourService.addTour(tourBody);
      return ResponseEntity.ok(tourBody);
    }
    return ResponseEntity.badRequest().body("Tour exist");
  }

  @DeleteMapping(path = "{idTour}")
  public void deleteTour(@PathVariable("idTour") Long idTour) {
    tourService.deleteTour(idTour);
  }

  @PutMapping(path = "/")
  public ResponseEntity updateTour(@RequestBody Tour tourBody) {
    if (tourBody == null) {
      return ResponseEntity.badRequest().body("Empty Request Body");
    }

    Optional<Tour> tour = Optional.ofNullable(tourService.getTourById(tourBody.getIdTour()));

    if (tour.isPresent()) {
      Tour createTour = tourService.updateTour(tourBody.getIdTour(), tourBody);
      return ResponseEntity.ok(tourBody);
    }
    return ResponseEntity.notFound().build();
  }
}
