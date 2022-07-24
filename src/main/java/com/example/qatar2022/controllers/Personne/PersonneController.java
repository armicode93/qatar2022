package com.example.qatar2022.controllers.Personne;


import com.example.qatar2022.entities.person.Personne;
import com.example.qatar2022.service.person.PersonneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController
@RequestMapping("v1/Personne")
@CrossOrigin(origins = "*")
public class PersonneController {

    private final PersonneService personneService;

    public PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }


    @GetMapping("/")
    public ResponseEntity findAll(){return ResponseEntity.ok(personneService.getAllPersonne());

    }
    //
     @GetMapping("/{PersonneId}")
    public ResponseEntity findPersonneById(@PathVariable(name = "PersonneId") Long personneId){
        if(personneId==null){
            return ResponseEntity.badRequest().body("Empty parameter");
        }
       Personne personne = personneService.getPersonne(personneId);

        if(personne != null){
            return ResponseEntity.ok(personne);
        }else{
            return  ResponseEntity.notFound().build();
        }
    }



    @CrossOrigin(origins = "*")
    @PostMapping("/Inscription")

    public ResponseEntity createPersonne(@RequestBody Personne personneBody)
    {
        if (personneBody == null)
        {
            return ResponseEntity.badRequest().body(("Empty Request Body"));
        }

        Optional <Personne> personne = personneService.findById(personneBody.getCin());

        if(!personne.isPresent())
        {
            Personne createPersonne = personneService.save(personneBody);
            return ResponseEntity.ok(createPersonne);
        }
        else {
            return ResponseEntity.badRequest().body("Personne already existe");
        }
    }

    @DeleteMapping("/{personneId}")
    public ResponseEntity deletePersonne(@PathVariable(name = "personneid") Long personneid)
    {
        if(personneCin == null)
        {
            return ResponseEntity.badRequest().body("Empty parameter");
        }

        Optional<Personne> personne = personneService.findById(personneid);

        if(personne.isPresent())
        {
            personneRepository.deleteById(personneCin);
            return ResponseEntity.ok().body(personne);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/")
    public ResponseEntity updatePersonne(@RequestBody Personne personneBody)

    {
        if(personneBody == null)
        {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional <Personne> personne = personneService.findById(personneBody.getCin());

        if (personne.isPresent()){
            Personne createPersonne = personneRepository.save(personneBody);
            return ResponseEntity.ok(createPersonne);
        }
        return ResponseEntity.notFound().build();
    }


}


