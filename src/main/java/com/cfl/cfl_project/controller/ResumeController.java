package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.LogBook;
import com.cfl.cfl_project.model.Resume;
import com.cfl.cfl_project.service.LogBookService;
import com.cfl.cfl_project.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/resume")
@CrossOrigin("*")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    private ResponseEntity<?> upload(@RequestParam Long empId, @RequestPart MultipartFile resumeFile)throws IOException {
        Resume resume = resumeService.uploadResume(empId, resumeFile);
        if(resume!=null){
            return ResponseEntity.ok(resume);
        }
        else{
            return ResponseEntity.status(400).body("Failed to upload your resume");
        }
    }


    @GetMapping("/download/{empId}")
    private ResponseEntity<?> download(@PathVariable Long empId) throws IOException {
        List<Resume> resumeList = resumeService.downloadResume(empId);
        if(!resumeList.isEmpty()){
            return ResponseEntity.ok(resumeList);
        }
        else{
            return ResponseEntity.status(404).body("No resume found for this employee");
        }
    }
}
