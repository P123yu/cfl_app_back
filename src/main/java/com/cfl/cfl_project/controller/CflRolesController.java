package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.CflRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cfl.cfl_project.service.CflRolesService;

@RestController
@RequestMapping("/cflRoles")
@CrossOrigin
public class CflRolesController {

    @Autowired
    private CflRolesService cflRolesService;

    @PostMapping("/create")
    public ResponseEntity<?>createRole(@RequestBody CflRoles cflRoles){
        CflRoles createdRole = cflRolesService.create(cflRoles);
        if(createdRole !=null){
            return ResponseEntity.ok(createdRole);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create CflRoles");
        }
    }


    @GetMapping("/getByYear/{year}")
    public ResponseEntity<?> getAllRolesByYear(@PathVariable Long year){
        CflRoles cflRoles=cflRolesService.getCflRoleByYear(year);
        if(cflRoles==null){
            return ResponseEntity.status(400).body("Year is required");
        }
        else{
            return ResponseEntity.ok(cflRoles);
        }
    }
}
