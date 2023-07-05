package com.example.qatar2022.controllers;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Image;
import com.example.qatar2022.entities.Partie;
import com.example.qatar2022.entities.Tour;
import com.example.qatar2022.service.EquipeService;
import com.example.qatar2022.service.ImageService;
import com.example.qatar2022.service.PartieService;
import com.example.qatar2022.service.TourService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/v1/Equipe")
@CrossOrigin(origins = "*")


//@CrossOrigin(origins = "*") //we execute a cross origin http request when it requests a resource that has a different origin, becouse front end running on 4200,end backend 8081

public class EquipeController {

    private final EquipeService equipeService;
    private final ImageService imageService;
    private final PartieService partieService;
    private final TourService tourService;

    public EquipeController(EquipeService equipeService, ImageService imageService, PartieService partieService, TourService tourService) {
        this.equipeService = equipeService;
        this.imageService = imageService;
        this.partieService = partieService;
        this.tourService = tourService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Equipe> equipes = equipeService.getAllEquipe();
        List<Image> images = imageService.getAllImage();
        List<Partie> parties = partieService.getAllPartie();
        List<Tour> tours = tourService.getallTour();

        model.addAttribute("parties", parties);
        model.addAttribute("tours", tours);
        model.addAttribute("equipes", equipes);
        model.addAttribute("images", images);
        model.addAttribute("title", "Liste des equipe");

        return "index";
    }
    /*

    @RequestMapping(value = "/listEquipe", method = RequestMethod.GET)

    public String listEquipes(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Equipe> equipePage = equipeService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("equipePage", equipePage);

        int totalPages = equipePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }

     */





    @GetMapping("/{idEquipe}")
    public ResponseEntity findEquipeById(@PathVariable(name = "idEquipe") Long idEquipe)
    {

        if(idEquipe==null){
            return ResponseEntity.badRequest().body("Empty parametre");
        }

        Optional<Equipe> equipes = Optional.ofNullable(equipeService.getEquipeById(idEquipe));



        if(equipes.isPresent()){
            return ResponseEntity.ok(equipes);
        }
        else{
            return  ResponseEntity.notFound().build();
        }
    }

   @GetMapping("/equipe/add")
   public String equipeFormAdd(Model model)
   {
       model.addAttribute(new Equipe());

       return"equipe/add";
   }

   @PostMapping("/equipe/add")
   public String equipeSubmitAdd(@Valid @ModelAttribute("equipe")Equipe equipe, @RequestParam("drapeau") MultipartFile drapeau,@RequestParam("pays") String pays,@RequestParam("nbr_points") Long nbr_points, BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
           return "equipe/add";
       }



       byte[] imageData = drapeau.getBytes();
       Image image = new Image();
       //
       image.setNom(image.getNom());
       image.setImageByte(imageData);

       equipeService.addEquipe(equipe);


       model.addAttribute("drapeau", equipe.getDrapeau());
       model.addAttribute("equipe", "");

       //model.addAttribute("image", "");

       return  "redirect:/";

   }

    @DeleteMapping("/equipe/delete/{idEquipe}")
    public String deleteEquipe (@PathVariable("idEquipe") Long idEquipe)
    {
        Equipe existing = equipeService.getEquipeById(idEquipe);
        if(existing!=null) {

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
    public String equipeSubmit(@Valid @ModelAttribute("equipe")Equipe equipe, BindingResult result, @PathVariable("idEquipe") Long idEquipe, Model model)
    {
        if (result.hasErrors()) {
            return "equipe/edit";
        }

        equipe.setIdEquipe(idEquipe);
        equipeService.updateEquipe(equipe.getIdEquipe(),equipe);

        model.addAttribute("equipe",equipe);


        return "redirect:/index";


    }





}






