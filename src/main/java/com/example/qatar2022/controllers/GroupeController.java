package com.example.qatar2022.controllers;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Groupe;
import com.example.qatar2022.repository.GroupeRepository;
import com.example.qatar2022.service.GroupeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/Groupe")
@CrossOrigin(origins="*")
public class GroupeController {

    private final GroupeService groupeService;

    public GroupeController(GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    @GetMapping("/")
    public ResponseEntity findAll()
    {
        return ResponseEntity.ok(groupeService.getAllGroupe());
    }
    @GetMapping("/{idGroupe}")
    public ResponseEntity findGroupeById(@PathVariable(name="idGroupe") Long idGroupe)
    {
        if(idGroupe == null)
        {
            return ResponseEntity.badRequest().body("Empty paramenre");
        }

        Groupe groupe = groupeService.getGroupeById(idGroupe);

        if(groupe != null)
        {
            return ResponseEntity.ok(groupe);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity createGroupe(@RequestBody Groupe groupeBody)
    {
        if (groupeBody == null)
        {
            return  ResponseEntity.badRequest().body("Empty Request Body");
        }
        Optional<Groupe> groupes= Optional.ofNullable(groupeService.getGroupeById(groupeBody.getIdGroupe()));


        if(!groupes.isPresent())
        {
           groupeService.addGroupe(groupeBody);
            return ResponseEntity.ok(groupeBody);
        }
        return  ResponseEntity.badRequest().body("Groupe exist");
    }

    @DeleteMapping(path="{idGroupe}")
    public void deleteGroupe(@PathVariable("idGroupe") Long idGroupe)
    {
        groupeService.deleteGroupe(idGroupe);
    }

    @PutMapping("/")
    public ResponseEntity updateGroupe (@RequestBody Groupe groupeBody)
    {
        if(groupeBody == null)
        {
            return ResponseEntity.badRequest().body("Empty Reuest body");
        }
        Optional<Groupe> groupes = Optional.ofNullable(groupeService.getGroupeById(groupeBody.getIdGroupe()));

        if(groupes.isPresent())
        {
            Groupe createGroupe = groupeService.updateGroupe(groupeBody.getIdGroupe(),groupeBody);
            return ResponseEntity.ok(groupeBody);
        }

        return ResponseEntity.notFound().build();
    }

}
