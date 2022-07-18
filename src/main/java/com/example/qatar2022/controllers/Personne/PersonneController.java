package com.example.qatar2022.controllers.Personne;


import com.example.qatar2022.entities.person.Personne;
import com.example.qatar2022.repository.person.PersonneRepository;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("v1/Personne")
@CrossOrigin(origins = "*")
public class PersonneController {

    private final PersonneRepository personneRepository;

    public PersonneController(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }


    @GetMapping("/")
    public ResponseEntity findAll(){return ResponseEntity.ok(personneRepository.findAll());

    }

    @GetMapping("/personne/{cin}")
    public ResponseEntity findAll(@PathVariable(name = "cin") Long cin)
    {
        return ResponseEntity.ok(personneRepository.findAllPersonne(cin));
    }
    public ResponseEntity findJoueurById(@PathVariable(name = "cin") Long cin)
    {
        if(cin == null){
            return ResponseEntity.badRequest().body("Empty parameter");
        }

        Optional<Personne> personne = personneRepository.findById(cin);

        if(personne.isPresent()){
            return ResponseEntity.ok(personne);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity createPersonne(@RequestBody Personne personneBody)
    {
        if (personneBody == null)
        {
            return ResponseEntity.badRequest().body(("Empty Request Body"));
        }

        Optional<Personne> personne = personneRepository.findById(personneBody.getCin());

        if(!personne.isPresent())
        {
            Personne createPersonne = personneRepository.save((personneBody));
            return ResponseEntity.ok(createPersonne);
        }
        else {
            return ResponseEntity.badRequest().body("Personne already existe");
        }
    }

    @DeleteMapping("/{personneCin}")
    public ResponseEntity deletePersonne(@PathVariable(name = "personneCin") Long personneCin)
    {
        if(personneCin == null)
        {
            return ResponseEntity.badRequest().body("Empty parameter");
        }

        Optional<Personne> personne = personneRepository.findById(personneCin);

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

        Optional <Personne> personne = personneRepository.findById(personneBody.getCin());

        if (personne.isPresent()){
            Personne createPersonne = personneRepository.save(personneBody);
            return ResponseEntity.ok(createPersonne);
        }
        return ResponseEntity.notFound().build();
    }
}
