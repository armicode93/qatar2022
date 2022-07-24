package com.example.qatar2022.service.person;

import com.example.qatar2022.entities.person.Personne;
import com.example.qatar2022.repository.person.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    public List<Personne> getAllPersonne()
    {
        List<Personne> personne = new ArrayList<>();

        personneRepository.findAll().forEach(personne::add);


        return personne;
    }

    public Personne getPersonne (Long cin)
    {
        return personneRepository.findById(cin).orElse(null);
    }

    public void addPersonne(Personne personne)
    {
        personneRepository.save(personne);
    }
    public void updatePersonne(Long cin, Personne personne)

    {
        personneRepository.save(personne);

    }
    public void deletePersonne(Long cin)
    {
        personneRepository.deleteById(cin);

    }



}
