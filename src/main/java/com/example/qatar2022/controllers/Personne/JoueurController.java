package com.example.qatar2022.controllers.Personne;

import com.example.qatar2022.entities.person.Joueur;
import com.example.qatar2022.repository.person.JoueurRepository;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/Joueur")
@CrossOrigin(origins = "*")
public class JoueurController {

    private final JoueurRepository joueurRepository;

    public JoueurController(JoueurRepository repository) {
        this.joueurRepository = repository;
    }


    @GetMapping("/")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(joueurRepository.findAll());
    }


    @GetMapping("/equipe/{IdEquipe}")
    public ResponseEntity findAll(@PathVariable(name = "IdEquipe") Long idEquipe) {
        return ResponseEntity.ok(joueurRepository.findAllByEquipe_IdEquipe(idEquipe));
    }

    @GetMapping("/{JoueurId}")
    public ResponseEntity findJoueurById(@PathVariable(name = "JoueurId") Long joueurId) {
        if (joueurId == null) {
            return ResponseEntity.badRequest().body("Empty parameter");
        }

        Optional<Joueur> joueur = joueurRepository.findById(joueurId);

        if (joueur.isPresent()) {
            return ResponseEntity.ok(joueur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity createJoueur(@RequestBody Joueur joueurBody) {
        if (joueurBody == null) {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional<Joueur> joueur = joueurRepository.findById(joueurBody.getCin());
        if (!joueur.isPresent()) {
            Joueur createJoueur = joueurRepository.save(joueurBody);
            return ResponseEntity.ok(createJoueur);
        }
        return ResponseEntity.badRequest().body("Joueur exist");
    }

    @DeleteMapping("/{joueurId}")
    public ResponseEntity deleteJoueur(@PathVariable(name = "joueurId") Long joueurNumber) {
        if (joueurNumber == null) {
            return ResponseEntity.badRequest().body("Empty parameter");
        }

        Optional<Joueur> joueur = joueurRepository.findById(joueurNumber);

        if (joueur.isPresent()) {
            joueurRepository.deleteById(joueurNumber);
            return ResponseEntity.ok().body(joueur);
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/")
    public ResponseEntity updateJoueur(@RequestBody Joueur joueurBody) {
        if (joueurBody == null) {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }


        Optional<Joueur> joueur = joueurRepository.findById(joueurBody.getCin());

        if (joueur.isPresent()) {
            Joueur createJoueur = joueurRepository.save(joueurBody);
            return ResponseEntity.ok(createJoueur);
        }
        return ResponseEntity.notFound().build();


    }
}

