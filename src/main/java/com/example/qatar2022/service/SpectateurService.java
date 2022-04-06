package com.example.qatar2022.service;


import com.example.qatar2022.entities.Spectateur;
import com.example.qatar2022.repository.SpectateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpectateurService {


    @Autowired
    SpectateurRepository  spectateurRepository;

    public List <Spectateur> getAllSpectateurs()
    {
        List <Spectateur> spectateurs = new ArrayList<>();

        spectateurRepository.findAll().forEach(spectateurs :: add);

        return spectateurs;
    }

    public Spectateur getSpectateur(String codeTicket)
    {
        int indice= Integer.parseInt(codeTicket);
        return spectateurRepository.findByCodeTicket(indice);
    }

    public void  addSpectateur (Spectateur spectateur)
    {
        spectateurRepository.save(spectateur);
    }
    public void updateSpectateur(Long codeTicket, Spectateur spectateur)
    {
        spectateurRepository.save(spectateur);
    }

    public void deleteSpectateur(Long codeTicket)
    {
        spectateurRepository.deleteById(codeTicket);
    }
}
