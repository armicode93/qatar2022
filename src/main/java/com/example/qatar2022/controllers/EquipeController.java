package com.example.qatar2022.controllers;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Image;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.service.EquipeService;
import com.example.qatar2022.service.ImageService;
import com.example.qatar2022.service.PartieService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/v1/Equipe")
//@CrossOrigin(origins = "*")


//@CrossOrigin(origins = "*") //we execute a cross origin http request when it requests a resource that has a different origin, becouse front end running on 4200,end backend 8081

public class EquipeController {

    private final EquipeService equipeService;
    private final ImageService imageService;
    private final PartieService partieService;

    public EquipeController(EquipeService equipeService, ImageService imageService, PartieService partieService) {
        this.equipeService =  equipeService;
        this.imageService = imageService;
        this.partieService = partieService;
    }

   @GetMapping("/")
    public String index(Model model)
    {
        List<Equipe> equipes = equipeService.getAllEquipe();
        List<Image> images = imageService.getAllImage();
        List<Partie> parties = partieService.getAllPartie();

        model.addAttribute("parties", parties);
        model.addAttribute("equipes",equipes);
        model.addAttribute("images",images);
        model.addAttribute("title","Liste des equipe");

        return "index";
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






