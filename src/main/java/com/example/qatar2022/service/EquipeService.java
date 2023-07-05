package com.example.qatar2022.service;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Image;
import com.example.qatar2022.repository.EquipeRepository;

import com.example.qatar2022.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final ImageRepository imageRepository;
   // final private List<Equipe> equipes = EquipeUtils.buildEquipes();




    @Autowired
    public EquipeService(EquipeRepository equipeRepository, ImageRepository imageRepository)
    {
        this.equipeRepository = equipeRepository;
        this.imageRepository = imageRepository;
    }


    public List<Equipe> getAllEquipe()
    {
        List<Equipe> equipes = new ArrayList<>();

        equipeRepository.findAll().forEach(equipes::add);
        return equipes;
    }



    public Equipe getEquipeById(Long idEquipe) {
        return equipeRepository.findById(idEquipe).orElse(null);
    }


    public void addEquipe( Equipe equipe )


    {

        equipe.setPays(equipe.getPays());
        equipe.setNbr_points(equipe.getNbr_points());
        equipe.setDrapeau(equipe.getDrapeau());



        equipeRepository.save(equipe);








    }



    public void deleteEquipe(Long idEquipe) {

        equipeRepository.deleteById(idEquipe);
    }

    public Equipe updateEquipe(Long idEquipe, Equipe equipe)
    {
        equipeRepository.save(equipe);


        return equipe;
    }


}
