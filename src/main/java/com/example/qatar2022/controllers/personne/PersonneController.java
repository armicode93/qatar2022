package com.example.qatar2022.controllers.personne;

import com.example.qatar2022.entities.personne.Personne;
import com.example.qatar2022.service.personne.PersonneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/Personne/")
@CrossOrigin(origins="*")
public class PersonneController {

    private final PersonneService personneService;

    public PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }


    @GetMapping("/")
    public ResponseEntity findAll()
    {
        return ResponseEntity.ok(personneService.getAllPersonne());
    }


    @GetMapping("/{PersonneId}")
    public ResponseEntity getPersonneById(@PathVariable (name = "PersonneId") Long personneId)
    {
        if(personneId==null){
            return ResponseEntity.badRequest().body("Empty parameter");
        }

        Optional<Personne> personne = Optional.ofNullable(personneService.getPersonneById(personneId));

        if(personne.isPresent()){
            return ResponseEntity.ok(personne);
        }else{
            return  ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity createPersonne(@RequestBody Personne personneBody)
    {
        if(personneBody == null)
        {
            return  ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional <Personne> personnes = Optional.ofNullable(personneService.getPersonneById(personneBody.getCin()));


        if(!personnes.isPresent())
        {
            personneService.addPersonne(personneBody);
            return ResponseEntity.ok(personneBody);
        }

        return ResponseEntity.badRequest().body("Personne exist");
    }

    @PutMapping(path="/")
    public ResponseEntity updatePersonne(@RequestBody Personne personneBody)
    {
        if(personneBody == null)
        {
            return  ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional<Personne> personne = Optional.ofNullable(personneService.getPersonneById(personneBody.getCin()));

        if(personne.isPresent())
        {
            Personne createPersonne = personneService.updatePersonne(personneBody.getCin(),personneBody);
            return ResponseEntity.ok(personneBody);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path="/{idPersonne}")
    public void deletePersonne(@PathVariable("idPersonne") Long idPersonne)
    {
       personneService.deletePersonne(idPersonne);
    }





}
