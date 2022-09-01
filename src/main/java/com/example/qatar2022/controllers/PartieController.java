package com.example.qatar2022.controllers;


import com.example.qatar2022.entities.Image;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.service.ImageService;
import com.example.qatar2022.service.PartieService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/Partie")
@CrossOrigin(origins = "*")
public class PartieController {

    private final PartieService partieService;
    private final ImageService imageService;

    public PartieController(PartieService partieService, ImageService imageService) {
        this.partieService = partieService;
        this.imageService = imageService;
    }


    @GetMapping("/")
    public String index(Model model)
    {
        List<Partie> parties = partieService.getAllPartie();
        List <Image> images = imageService.getAllImage();

        model.addAttribute("parties", parties);
        model.addAttribute("images", images);
        model.addAttribute("title","Liste des matches");

        return "index";
    }

    @GetMapping("/{idPartie}")
    public ResponseEntity findPartieById(@PathVariable(name="idPartie")Long idPartie)
    {
         if(idPartie == null)
         {
             return ResponseEntity.badRequest().body("Empty Parametre");
         }

         Optional <Partie> parties = Optional.ofNullable(partieService.getPartieById(idPartie));


         if(parties.isPresent())
         {
             return ResponseEntity.ok(parties);
         }
         else
         {
             return ResponseEntity.notFound().build();
         }
    }

    @PostMapping("/")
    public ResponseEntity createPartie(@RequestBody Partie partieBody) {
        if (partieBody == null) {
            return ResponseEntity.badRequest().body("Empty Request body");
        }

        Optional<Partie> parties = Optional.ofNullable(partieService.getPartieById(partieBody.getIdPartie()));

        if (!parties.isPresent()) {
            partieService.addPartie(partieBody);
            return ResponseEntity.ok(partieBody);
        } else {
            return ResponseEntity.badRequest().body("Partie exist");
        }
    }

        @DeleteMapping(path ="{idPartie}")
        public void deletePartie(@PathVariable("idPartie") Long idPartie)

        {
           partieService.deletePartie(idPartie);
        }

    @PutMapping(path = "/" )
    public ResponseEntity updatePartie (@RequestBody Partie partieBody) {


        if (partieBody == null) {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional<Partie> partie = Optional.ofNullable(partieService.getPartieById(partieBody.getIdPartie()));

        if (partie.isPresent()) {
            Partie createPartie = partieService.updatePartie(partieBody.getIdPartie(), partieBody);
            return ResponseEntity.ok(partieBody);
        }
        return ResponseEntity.notFound().build();
    }


}
