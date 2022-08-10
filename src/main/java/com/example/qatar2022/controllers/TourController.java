package com.example.qatar2022.controllers;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Tour;
import com.example.qatar2022.service.TourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/Tour")
@CrossOrigin(origins = "*")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/")
    public ResponseEntity findAll()
    {
        return ResponseEntity.ok(tourService.getallTour());
    }

    @GetMapping("{idTour}")
    public ResponseEntity findTourById(@PathVariable(name = "idTour")Long idTour)
    {
        if(idTour == null)
        {
            return ResponseEntity.badRequest().body("Empty parametre");
        }
        Optional<Tour> tours = Optional.ofNullable(tourService.getTourById(idTour));





        if(tours.isPresent()){
            return ResponseEntity.ok(tours);
        }
        else{
            return  ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity createTour(@RequestBody Tour tourBody)
    {

        if(tourBody==null)

        {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional <Tour> tours = Optional.ofNullable(tourService.getTourById(tourBody.getIdTour()));

        if(!tours.isPresent())
        {
            tourService.addTour(tourBody);
            return ResponseEntity.ok(tourBody);
        }
        return ResponseEntity.badRequest().body("Tour exist");
    }

    @DeleteMapping(path="{idTour}")
    public void deleteTour (@PathVariable("idTour") Long idTour)
    {
        tourService.deleteTour(idTour);
    }

    @PutMapping(path = "/")
    public ResponseEntity updateTour (@RequestBody Tour tourBody)
    {
        if (tourBody==null){
            return  ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional <Tour> tour = Optional.ofNullable(tourService.getTourById(tourBody.getIdTour()));

        if(tour.isPresent())
        {
            Tour createTour = tourService.updateTour(tourBody.getIdTour(),tourBody);
            return ResponseEntity.ok(tourBody);
        }
        return ResponseEntity.notFound().build();
    }
}
