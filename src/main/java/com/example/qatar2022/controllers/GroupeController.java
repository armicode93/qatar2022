package com.example.qatar2022.controllers;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Groupe;
import com.example.qatar2022.service.EquipeService;
import com.example.qatar2022.service.GroupeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller

@CrossOrigin(origins="*")
public class GroupeController {

    private final GroupeService groupeService;

    private final EquipeService equipeService;

    public GroupeController(GroupeService groupeService, EquipeService equipeService) {
        this.groupeService = groupeService;
        this.equipeService = equipeService;
    }

    @GetMapping("/groupe ")
    public String index(Model model)
    {
        List<Groupe> groupes = groupeService.getAllGroupe();
        List<Equipe> equipes= equipeService.getAllEquipe();

        model.addAttribute("groupes",groupes);
        model.addAttribute("equipes",equipes);

        return "groupe/index.html";


    }


}
