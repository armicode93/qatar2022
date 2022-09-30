package com.example.qatar2022.controllers.personne;


import com.example.qatar2022.entities.personne.Staff;
import com.example.qatar2022.service.personne.StaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/Staff")
@CrossOrigin(origins = "*")

public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping("/")
    public ResponseEntity findAll()
    {
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @GetMapping("/{idStaff}")
    public ResponseEntity findStaffById(@PathVariable(name = "idStaff") Long idStaff)
    {
        if(idStaff ==null)
        {
            return ResponseEntity.badRequest().body("Empty paramentre");
        }

        Optional <Staff> staff = Optional.ofNullable(staffService.getStaffById(idStaff));


        if(staff.isPresent())
        {
            return ResponseEntity.ok(staff);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity createStaff(@RequestBody Staff staffBody)
    {
        if(staffBody == null)
        {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }
        Optional <Staff> staff = Optional.ofNullable(staffService.getStaffById(staffBody.getIdStaff()));

        if(!staff.isPresent())
        {
            staffService.addStaff(staffBody);
            return ResponseEntity.ok(staffBody);
        }

        return ResponseEntity.badRequest().body("Staff exist");
    }

    @DeleteMapping(path = "{idStaff}")
    public void deleteStaff(@PathVariable("idStaff") Long idStaff)
    {
        staffService.deleteStaff(idStaff);
    }

    @PutMapping(path ="/")
    public ResponseEntity updateStaff(@RequestBody Staff staffBody)
    {
        if(staffBody == null)
        {
            return ResponseEntity.badRequest().body("Empty Request Body");
        }

        Optional <Staff> staff = Optional.ofNullable(staffService.getStaffById(staffBody.getIdStaff()));

        if(staff.isPresent())
        {
            Staff createStaff = staffService.updateStaff(staffBody.getIdStaff(), staffBody);
            return ResponseEntity.ok(staffBody);
        }
        return ResponseEntity.notFound().build();
    }
}
