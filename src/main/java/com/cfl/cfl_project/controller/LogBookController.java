package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.dto.CflLogBookCflTableDTO;
import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.model.LogBook;
import com.cfl.cfl_project.service.LogBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/logbook")
@CrossOrigin("*")
public class LogBookController {

    @Autowired
    private LogBookService logBookService;

    @PostMapping("/upload")
    private ResponseEntity<?> upload(@RequestParam Long empId, @RequestPart MultipartFile LogBookFile)throws IOException {
        LogBook logBookFile = logBookService.uploadLogBook(empId, LogBookFile);
        if(logBookFile!=null){
            return ResponseEntity.ok(logBookFile);
        }
        else{
            return ResponseEntity.status(400).body("Failed to upload mentoring logbook");
        }
    }


    @GetMapping("/download/{empId}")
    private ResponseEntity<?> download(@PathVariable Long empId) throws IOException {
        List<LogBook> logBookFile = logBookService.downloadLogBook(empId);
        if(!logBookFile.isEmpty()){
            return ResponseEntity.ok(logBookFile);
        }
        else{
            return ResponseEntity.status(404).body("No logbook found for this employee");
        }
    }


    @GetMapping("/downloadLogBookReport")
    public ResponseEntity<?> downloadLogBookReport(){
        List<CflLogBookCflTableDTO> cflLogBookCflTableDTOS=logBookService.downloadLogBookReport();
        if(!cflLogBookCflTableDTOS.isEmpty()){
            return ResponseEntity.ok(cflLogBookCflTableDTOS);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get all cflLogBookCflTableDTOS ");
        }
    }
}
