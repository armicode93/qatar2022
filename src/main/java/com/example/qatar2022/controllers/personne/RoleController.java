package com.example.qatar2022.controllers.personne;

import com.example.qatar2022.entities.personne.Role;
import com.example.qatar2022.service.personne.RoleService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoleController {

  @Autowired RoleService service;

  @GetMapping("/roles")
  public String index2(Model model) {
    List<Role> roles = service.getAllRole();
    model.addAttribute("roles", roles);
    model.addAttribute("title", "Liste de role");

    return "role/index";
  }

  @GetMapping("/roles/{id}")
  public String show(Model model, @PathVariable("id") String id) {
    Role role = service.getRole(id);

    model.addAttribute("role", role);
    model.addAttribute("Title", "Ficher d'un role");

    return "role/show";
  }

  @GetMapping("/roles/add")
  public String rolesFormAdd(Model model) {
    model.addAttribute(new Role());
    return "role/addRole";
  }

  @PostMapping("/roles/add")
  public String roleSubmitAdd(
      @Valid @ModelAttribute("role") Role role,
      BindingResult result,
      Model model) // model attribute pour recuper les input de les champsque on aa remplis
      {

    if (result.hasErrors()) {
      return "role/addRole";
    }

    role.setName(role.getName());

    model.addAttribute(new Role());
    service.add(role);

    return "redirect:/roles";
  }

  @GetMapping("roles/add/{id}")
  public String roleEditForm(Model model, @PathVariable("id") String id) {
    Role role = service.getRole(id);

    
    model.addAttribute("role", role);
    model.addAttribute("title", "Fiche d'un role");

    return "role/editRole";
  }

  @PostMapping("/roles/add/{id}")
  public String roleEditSubmit(@Valid Role role, BindingResult result, ModelMap model) {

    if (result.hasErrors()) {
      return "role/editRole";
    }

    service.add(role);

    return "role/show";
  }

  @DeleteMapping("/roles/delete/{id}")
  public String delete(@PathVariable("id") String id, Model model) {
    Role existing = service.getRole(id);
    if (existing != null) {
      Long indice = (long) Integer.parseInt(id);

      service.delete(indice);
    }
    return "redirect:/roles";
  }
}
