package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.UserManual;
import com.cfl.cfl_project.service.UserManualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user-manual")
@CrossOrigin("*")
public class UserManualController {

    @Autowired
    private UserManualService userManualService;

    @PostMapping("/upload")
    private ResponseEntity<?> upload(@RequestParam Long Id, @RequestPart MultipartFile userManualFile)throws IOException {
        UserManual userManual = userManualService.uploadUserManual(Id, userManualFile);
        if(userManual!=null){
            return ResponseEntity.ok(userManual);
        }
        else{
            return ResponseEntity.status(400).body("Failed to upload userManual");
        }
    }


    @GetMapping("/download/{Id}")
    private ResponseEntity<?> download(@PathVariable Long Id) throws IOException {
        UserManual userManual = userManualService.downloadUserManual(Id);
        if(userManual !=null ){
            return ResponseEntity.ok(userManual);
        }
        else{
            return ResponseEntity.status(404).body("No userManual found for this employee");
        }
    }
}
