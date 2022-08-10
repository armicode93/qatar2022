package com.example.qatar2022.controllers;


import com.example.qatar2022.entities.Equipe;
import com.example.qatar2022.entities.Type;
import com.example.qatar2022.service.TypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/Type")
@CrossOrigin(origins = "*")

public class TypeController {

    private  final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }



    @GetMapping("/")

    public ResponseEntity findAll()
    {
       return ResponseEntity.ok(typeService.getAllType());
    }

    @GetMapping("/{idType}")
    public ResponseEntity findTypeById(@PathVariable(name="idType") Long idType)
    {
        if(idType==null){
            return ResponseEntity.badRequest().body("Empty parametre");
        }

        Optional<Type> types = Optional.ofNullable(typeService.getTypeById(idType));





        if(types.isPresent()){
            return ResponseEntity.ok(types);
        }
        else{
            return  ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity createType (@RequestBody Type typeBody)
    {
        if (typeBody==null){
            return  ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional<Type> types= Optional.ofNullable(typeService.getTypeById(typeBody.getIdType()));


        if (!types.isPresent()){
            typeService.addType(typeBody);
            return ResponseEntity.ok(typeBody);
        }
        return ResponseEntity.badRequest().body("Type exist");
    }

    @DeleteMapping(path = "{idType}")
    public void deleteType(@PathVariable("idType") Long idType)
    {
        typeService.deleteType(idType);
    }

    @PutMapping(path="/")
    public ResponseEntity updateType(@RequestBody Type typeBody)
    {
        if (typeBody==null){
            return  ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional <Type> type = Optional.ofNullable(typeService.getTypeById(typeBody.getIdType()));

        if(type.isPresent())
        {
            typeService.updateType(typeBody.getIdType(),typeBody);

            return ResponseEntity.ok(typeBody);
        }

        return ResponseEntity.notFound().build();
    }
}
