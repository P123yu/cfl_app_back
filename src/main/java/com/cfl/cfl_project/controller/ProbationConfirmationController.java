package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.dto.CflProbationConfirmationCflTableDTO;
import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.model.ProbationConfirmation;
import com.cfl.cfl_project.service.ProbationConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/probationConfirmation")
@CrossOrigin("*")

public class ProbationConfirmationController {

    @Autowired
    private ProbationConfirmationService probationConfirmationService;

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody ProbationConfirmation probationConfirmation){
        ProbationConfirmation probationConfirmationObj=probationConfirmationService.create(probationConfirmation);
        if(probationConfirmationObj!=null){
            return ResponseEntity.ok(probationConfirmationObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create ProbationConfirmation");
        }
    }



    @GetMapping("/getByEmpId/{empId}")
    public ResponseEntity<?>getByEmployeeCode(@PathVariable Long empId){
        ProbationConfirmation probationConfirmationObj=probationConfirmationService.getByEmployeeCode(empId);
        if(probationConfirmationObj!=null){
            return ResponseEntity.ok(probationConfirmationObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get Probation Confirmation");
        }
    }


    @GetMapping("/getAllProbationConfirmationReport")
    public ResponseEntity<?> getAllProbationConfirmationReport(){
        List<CflProbationConfirmationCflTableDTO> cflProbationConfirmationCflTableDTOs=probationConfirmationService.cflProbationConfirmationCflTableDTOs();
        if(!cflProbationConfirmationCflTableDTOs.isEmpty()){
            return ResponseEntity.ok(cflProbationConfirmationCflTableDTOs);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get all cflProbationConfirmationCflTableDTOs ");
        }
    }


    @GetMapping("/getAllProbationConfirmationStatus/{year}")
    public ResponseEntity<?> findAllProbationStatusByYear(@PathVariable Long year) {
        List<ProbationConfirmation>probationConfirmationList=probationConfirmationService.findAllProbationStatusByYear(year);
        if(!probationConfirmationList.isEmpty()){
            return ResponseEntity.ok(probationConfirmationList);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/editProbation/{empId}")
    public ResponseEntity<?> editProbationConfirmation(@PathVariable Long empId) {
        ProbationConfirmation probationConfirmation=probationConfirmationService.editProbationConfirmation(empId);
        return probationConfirmation !=null ? ResponseEntity.ok(probationConfirmation) : ResponseEntity.notFound().build();
    }





}
