package com.example.qatar2022.service.personne;


import com.example.qatar2022.entities.personne.Personne;
import com.example.qatar2022.repository.personne.PersonneRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonneService {

    private final PersonneRepository personneRepository;

    public PersonneService(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }





    public List <Personne> getAllPersonne()
    {
        List<Personne> personnes = new ArrayList<>();

        personneRepository.findAll().forEach(personnes:: add);

        return personnes;
    }

    public Personne getPersonneById(Long idPersonne)
    {
        return personneRepository.findById(idPersonne).orElse(null);
    }

    public void addPersonne(Personne personne)
    {
        personneRepository.save(personne);
    }

    public void deletePersonne(Long idPersonne)
    {
        boolean exists = personneRepository.existsById(idPersonne);
        if(!exists)
        {
            throw new IllegalStateException("Not exists");
        }
        personneRepository.deleteById((idPersonne));
    }

    public Personne updatePersonne(Long idPersonne, Personne personne)
    {
        personneRepository.save(personne);
        return personne;
    }
}
