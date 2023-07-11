package com.example.qatar2022.controllers;

import com.example.qatar2022.service.EquipeService;
import com.example.qatar2022.service.PartieService;
import com.example.qatar2022.service.PosteService;
import com.example.qatar2022.service.personne.JoueurService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*")
public class PosteController {

  private final PosteService posteService;

  private final JoueurService joueurService;

  private final PartieService partieService;

  private final EquipeService equipeService;

  public PosteController(
      PosteService posteService,
      JoueurService joueurService,
      PartieService partieService,
      EquipeService equipeService) {
    this.posteService = posteService;
    this.joueurService = joueurService;
    this.partieService = partieService;
    this.equipeService = equipeService;
  }
}