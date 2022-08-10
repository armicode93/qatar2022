package com.example.qatar2022.controllers;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.service.EquipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/Equipe")
@CrossOrigin(origins = "*")

public class EquipeController {

    private final EquipeService equipeService;

    public EquipeController(EquipeService equipeService) {
        this.equipeService =  equipeService;
    }

    @GetMapping("/")
    public ResponseEntity findAll()
    {
        return ResponseEntity.ok(equipeService.getAllEquipe());
    }

    @GetMapping("/{idEquipe}")
    public ResponseEntity findEquipeById(@PathVariable(name = "idEquipe") Long idEquipe)
    {
        if(idEquipe==null){
            return ResponseEntity.badRequest().body("Empty parametre");
        }

        Optional<Equipe> equipes = Optional.ofNullable(equipeService.getEquipeById(idEquipe));





        if(equipes.isPresent()){
            return ResponseEntity.ok(equipes);
        }
        else{
            return  ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/")
    public ResponseEntity createEquipe(@RequestBody Equipe equipeBody)
    {
        if (equipeBody==null){
            return  ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional<Equipe> equipes= Optional.ofNullable(equipeService.getEquipeById(equipeBody.getIdEquipe()));


        if (!equipes.isPresent()){
            equipeService.addEquipe(equipeBody);
            return ResponseEntity.ok(equipeBody);
        }
        return ResponseEntity.badRequest().body("Equipe exist");
    }

    @DeleteMapping(path ="{idEquipe}")
    public void  deleteEquipe (@PathVariable("idEquipe") Long idEquipe)
    {

        equipeService.deleteEquipe(idEquipe);
    }

    @PutMapping(path = "/" )
    public ResponseEntity updateEquipe (@RequestBody Equipe equipeBody){
        if (equipeBody==null){
            return  ResponseEntity.badRequest().body("Empty Request Body");
        }


        Optional<Equipe> equipe= Optional.ofNullable(equipeService.getEquipeById(equipeBody.getIdEquipe()));

        if (equipe.isPresent()){
            Equipe createEquipe = equipeService.updateEquipe(equipeBody.getIdEquipe(),equipeBody);
            return ResponseEntity.ok(equipeBody);
        }
        return ResponseEntity.notFound().build();




    }



}






