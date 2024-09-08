package com.cfl.cfl_project.controller;


import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.model.CflSkill;
import com.cfl.cfl_project.service.CflSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cflSkill")
@CrossOrigin("*")
public class CflSkillController {

    @Autowired
    private CflSkillService cflSkillService;

    @PostMapping("/create/{quarter}")
    public ResponseEntity<?> createCflSkill(@PathVariable String quarter,@RequestBody CflSkill cflSkill){
        CflSkill cflSkillObj=cflSkillService.createCflSkill(quarter,cflSkill);
        if(cflSkillObj !=null){
            return ResponseEntity.ok(cflSkillObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create Cfl Skill");
        }
    }



    @GetMapping("/get/{empId}/{quarter}/{year}")
    public ResponseEntity<?> getCflSkillByIdAndQuarter(@PathVariable("empId") Long id,@PathVariable String quarter,@PathVariable Long year){
        CflSkill cflSkillObj=cflSkillService.getCflSkillByEmpIdAndQuarterAndYear(id,quarter,year);
        if(cflSkillObj !=null){
            return ResponseEntity.ok(cflSkillObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get Cfl Skill");
        }
    }

    @GetMapping("/getAllCflSkill")
    public ResponseEntity<?> getAllCflSkills(){
        List<CflSkill> cflSkills=cflSkillService.getAll();
        if(!cflSkills.isEmpty()){
            return ResponseEntity.ok(cflSkills);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get all Cfl Skills");
        }
    }


    @GetMapping("/getAllCflSkillByYear/{year}")
    public ResponseEntity<?> getAllCflSkillsByYear(@PathVariable Long year){
        List<CflSkill> cflSkills=cflSkillService.getAllByYear(year);
        if(!cflSkills.isEmpty()){
            return ResponseEntity.ok(cflSkills);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get all Cfl Skills");
        }
    }



    @GetMapping("/getAllCflSkillReport")
    public ResponseEntity<?> getAllCflSkillsReports(){
        List<CflSkillCflTableDTO> cflSkillCflTableDTOS=cflSkillService.getAllReports();
        if(!cflSkillCflTableDTOS.isEmpty()){
            return ResponseEntity.ok(cflSkillCflTableDTOS);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get all cflSkillCflTableDTOS ");
        }
    }


    @GetMapping("/getCflSkillByEmpId/{empId}")
    public ResponseEntity<?> getCflSkillByEmpId(@PathVariable Long empId){
        List<CflSkill> cflSkill=cflSkillService.getCflSkillByEmpId(empId);
        if(!cflSkill.isEmpty()){
            return ResponseEntity.ok(cflSkill);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get all Cfl Skills");
        }
    }

}
