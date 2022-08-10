package com.example.qatar2022.controllers.personne;

import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.service.personne.JoueurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/Joueur")
@CrossOrigin(origins = "*")
public class JoueurController {

    private final JoueurService joueurService;

    public JoueurController(JoueurService joueurService) {
        this.joueurService = joueurService;
    }


    @GetMapping("/")
    public ResponseEntity findAll() {
        return ResponseEntity.ok(joueurService.getAllJoueur());
    }


    @GetMapping("/equipe/{IdEquipe}")
    public ResponseEntity findAll(@PathVariable(name = "IdEquipe") Long idEquipe) {
        return ResponseEntity.ok(joueurService.getAllJoueurByEquipe(idEquipe));
    }

    @GetMapping("/{JoueurId}")
    public ResponseEntity findJoueurById(@PathVariable(name = "JoueurId") Long joueurId) {
        if (joueurId == null) {
            return ResponseEntity.badRequest().body("Empty parameter");
        }

        Optional<Joueur> joueur = Optional.ofNullable(joueurService.getJoueurById(joueurId));

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

        Optional<Joueur> joueur = Optional.ofNullable(joueurService.getJoueurById(joueurBody.getCin()));
        if (!joueur.isPresent()) {

            joueurService.addJoueur(joueurBody);
            return ResponseEntity.ok(joueurBody);
        }
        return ResponseEntity.badRequest().body("Joueur exist");
    }

    @DeleteMapping("/{joueurId}")
    public void deleteJoueur(@PathVariable(name = "joueurId") Long joueurNumber)
    {
      joueurService.deleteJoueur(joueurNumber);
    }

    @PutMapping("/")
    public ResponseEntity updateJoueur(@RequestBody Joueur joueurBody) {
        if (joueurBody == null) {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }


        Optional<Joueur> joueur = Optional.ofNullable(joueurService.getJoueurById(joueurBody.getCin()));

        if (joueur.isPresent()) {
            Joueur createJoueur = joueurService.updateJoueur(joueurBody.getCin(),joueurBody);
            return ResponseEntity.ok(createJoueur);
        }
        return ResponseEntity.notFound().build();


    }
}
