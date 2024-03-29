package com.example.qatar2022.controllers;

import com.example.qatar2022.config.FileUploadUtil;
import com.example.qatar2022.entities.*;
import com.example.qatar2022.entities.personne.Joueur;
import com.example.qatar2022.entities.personne.Staff;
import com.example.qatar2022.service.*;
import com.example.qatar2022.service.personne.JoueurService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.qatar2022.service.personne.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin(
    origins =
        "*") // @CrossOrigin(origins = "*") //we execute a cross origin http request when it
             // requests a resource
// that has a different origin, becouse front end running on 4200,end backend 8081, when i used
// angular

public class EquipeController {

  private final EquipeService equipeService;
  private final PartieService partieService;
  private final TourService tourService;
  private final JoueurService joueurService;
  private final PosteService posteService;
  private final StaffService staffService;

  public EquipeController(
          EquipeService equipeService,
          PartieService partieService,
          TourService tourService,
          JoueurService joueurService,
          PosteService posteService, StaffService staffService) {
    this.equipeService = equipeService;
    this.partieService = partieService;
    this.tourService = tourService;
    this.joueurService = joueurService;
    this.posteService = posteService;
    this.staffService = staffService;
  }

  @GetMapping("/")
  public String index(Model model) {
    List<Equipe> equipes = equipeService.getAllEquipe();
    List<Partie> parties = partieService.getAllPartie();
    List<Tour> tours = tourService.getallTour();

    model.addAttribute("parties", parties);
    model.addAttribute("tours", tours);
    model.addAttribute("equipes", equipes);
    model.addAttribute("title", "Liste des equipe");

    return "index";
  }

  @GetMapping("/equipe/{idEquipe}")
  public String equipeDetail(@PathVariable("idEquipe") Long idEquipe,HttpSession session, Model model) {



    Equipe equipe = equipeService.getEquipeById(idEquipe);
    List<Joueur> joueurs = joueurService.getAllJoueurByEquipe(idEquipe);
    List<Poste> postes = posteService.getAllPoste();
    List<Staff> staff = staffService.getStaffByEquipe(idEquipe);


    model.addAttribute("equipe", equipe);
    model.addAttribute("staff", staff);
    model.addAttribute("joueurs", joueurs);
    model.addAttribute("postes", postes);

    return "equipe/detail";
  }

  @GetMapping("/equipe/add")
  public String equipeFormAdd(Model model) {
    model.addAttribute(new Equipe());

    return "equipe/add";
  }

  @PostMapping(value = "/equipe/add")
  public String equipeSubmitAdd(
      @ModelAttribute("equipe") Equipe equipe,
      BindingResult result,
      @RequestParam("image") MultipartFile multipartFile,
      @RequestParam("pays") String pays,
      ModelMap model)
      throws IOException {

    if (result.hasErrors()) {
      return "equipe/add";
    }

    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    equipe.setDrapeau(fileName);
    equipe.setPays(equipe.getPays());

    equipeService.addEquipe(equipe);

    String uploadDir = "./src/main/resources/static/images/equipe/" + equipe.getIdEquipe();

    FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

    model.addAttribute("equipe", equipe);

    return "redirect:/";
  }

  @DeleteMapping("/equipeDelete/{idEquipe}")
  public String deleteEquipe(@PathVariable("idEquipe") Long idEquipe) {
    Equipe existing = equipeService.getEquipeById(idEquipe);
    if (existing != null) {

      equipeService.deleteEquipe(idEquipe);
    }

    return "redirect:/";
  }

  @GetMapping("/equipe/edit/{idEquipe}")
  public String equipeForm(Model model, @PathVariable("idEquipe") Long idEquipe) {
    Equipe equipe = equipeService.getEquipeById(idEquipe);

    model.addAttribute("equipe", equipe);

    return "equipe/edit";
  }

  @PostMapping("/equipe/edit/{idEquipe}")
  public String equipeSubmit(
      @Valid @ModelAttribute("equipe") Equipe equipe,
      BindingResult result,
      @PathVariable("idEquipe") Long idEquipe,
      Model model) {
    if (result.hasErrors()) {
      return "equipe/edit";
    }

    equipe.setIdEquipe(idEquipe);
    equipeService.updateEquipe(equipe.getIdEquipe(), equipe);

    model.addAttribute("equipe", equipe);

    return "redirect:/index";
  }
}
