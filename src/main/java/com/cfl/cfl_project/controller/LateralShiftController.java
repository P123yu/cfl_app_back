package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.LateralShift;
import com.cfl.cfl_project.service.LateralShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/lateral-shift")
public class LateralShiftController {

    @Autowired
    private LateralShiftService lateralShiftService;

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody LateralShift lateralShift){
        LateralShift lateralShiftObj=lateralShiftService.create(lateralShift);
        if(lateralShiftObj !=null){
            return ResponseEntity.ok(lateralShiftObj);
        }
        else{
            return ResponseEntity.internalServerError().build();
        }
    }



    @GetMapping("/getByEmpIdAndYear/{empId}/{year}")
    public ResponseEntity<?>getByEmpIdAndYear(@PathVariable Long empId, @PathVariable Long year){
        LateralShift lateralShiftObj=lateralShiftService.getByEmpIdAndYear(empId,year);
        if(lateralShiftObj !=null){
            return ResponseEntity.ok(lateralShiftObj);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
