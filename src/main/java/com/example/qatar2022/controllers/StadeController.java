package com.example.qatar2022.controllers;

import com.example.qatar2022.entities.Stade;
import com.example.qatar2022.service.StadeService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@CrossOrigin(origins = "*")
public class StadeController {

  private final StadeService stadeService;

  public StadeController(StadeService stadeService) {
    this.stadeService = stadeService;
  }

  @GetMapping("/stade")
  public ResponseEntity findAll() {
    return ResponseEntity.ok(stadeService.getAllStade());
  }

  @GetMapping("{idStade}")
  public ResponseEntity findStadeById(@PathVariable(name = "idStade") Long idStade) {
    if (idStade == null) {
      return ResponseEntity.badRequest().body("Empty parametre");
    }

    Optional<Stade> stades = Optional.ofNullable(stadeService.getStadeById(idStade));

    if (stades.isPresent()) {
      return ResponseEntity.ok(stades);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/")
  public ResponseEntity createStade(@RequestBody Stade stadeBody) {
    if (stadeBody == null) {
      return ResponseEntity.badRequest().body("Empty Request Body");
    }

    Optional<Stade> stades = Optional.ofNullable(stadeService.getStadeById(stadeBody.getIdStade()));

    if (!stades.isPresent()) {
      stadeService.addStade(stadeBody);

      return ResponseEntity.ok(stadeBody);
    }
    return ResponseEntity.badRequest().body("Stade exists");
  }

  @DeleteMapping(path = "{idStade}")
  public void deleteStade(@PathVariable("idStade") Long idStade) {
    stadeService.deleteStade(idStade);
  }

  @PutMapping(path = "/")
  public ResponseEntity updateStade(@RequestBody Stade stadeBody) {
    if (stadeBody == null) {
      return ResponseEntity.badRequest().body("Empty Request Body");
    }

    Optional<Stade> stades = Optional.ofNullable(stadeService.getStadeById(stadeBody.getIdStade()));

    if (stades.isPresent()) {
      Stade createStade = stadeService.updateStade(stadeBody.getIdStade(), stadeBody);

      return ResponseEntity.ok(stadeBody);
    }
    return ResponseEntity.notFound().build();
  }
}
