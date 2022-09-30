package com.example.qatar2022.service;


import com.example.qatar2022.entities.Groupe;
import com.example.qatar2022.repository.GroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupeService {

    private final GroupeRepository groupeRepository;



    @Autowired
    public GroupeService(GroupeRepository groupeRepository) {
        this.groupeRepository = groupeRepository;
    }


    public List<Groupe> getAllGroupe()
    {
        List<Groupe> groupes= new ArrayList<>();
        groupeRepository.findAll().forEach(groupes::add);
        return groupes;
    }

    public Groupe getGroupeById(Long idGroupe)
    {
        return groupeRepository.findById(idGroupe).orElse(null);
    }

    public void addGroupe(Groupe groupe) {
        groupeRepository.save(groupe);
    }

    public Groupe updateGroupe(Long idGroupe, Groupe groupe)
    {
        groupeRepository.save(groupe);
        return groupe;
    }
    public void deleteGroupe(Long idGroupe)
    {
         groupeRepository.deleteById(idGroupe);
    }

}

