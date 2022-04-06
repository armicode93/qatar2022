package com.example.qatar2022.service;


import com.example.qatar2022.entities.person.Personne;
import com.example.qatar2022.repository.person.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonneService {

    @Autowired
    private PersonneRepository personneRepository;

    public List<Personne> getAllPersonnes()
    {
        List<Personne> personnes = new ArrayList<>();

         personneRepository.findAll().forEach(personnes::add);

         return personnes;

    }
    public Personne getPersonne (String cin)
    {
        int indice = Integer.parseInt(cin);

        return personneRepository.findByCin(indice);
    }

    public void addPersonne (Personne personne)
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
