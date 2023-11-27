package com.example.qatar2022.controllers.personne;

import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Poste;
import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.entities.personne.Staff;
import com.example.qatar2022.service.EquipeService;
import com.example.qatar2022.service.personne.StaffService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller

@CrossOrigin(origins = "*")
public class StaffController {

  private final StaffService staffService;
  private final EquipeService equipeService;

  public StaffController(StaffService staffService, EquipeService equipeService) {
    this.staffService = staffService;
    this.equipeService = equipeService;
  }



  @GetMapping("/addStaff/{idEquipe}")
  public String StaffFormAdd(Model model, @PathVariable("idEquipe") Long idEquipe) {

    Equipe equipe = equipeService.getEquipeById(idEquipe);



    model.addAttribute("equipe", equipe);
    model.addAttribute("staff", new Staff());


    return "staff/add";
  }

  @PostMapping("/addStaff/{idEquipe}")
  public String StaffSubmitAdd(
          @Valid @ModelAttribute("staff") Staff staff,
          BindingResult result,
          @PathVariable(name = "idEquipe") Long idEquipe,


          ModelMap model) {

    if (result.hasErrors()) {
      return "staff/add";
    }
    Equipe equipe = equipeService.getEquipeById(idEquipe);


    staff.setNom(staff.getNom());
    staff.setPrenom(staff.getPrenom());
    staff.setFonction(staff.getFonction());
    staff.setEquipe(equipe);

    staffService.addStaff(staff);



    model.addAttribute("staff", staff);
    model.addAttribute("idEquipe", idEquipe);

    return "redirect:/";
  }
}
