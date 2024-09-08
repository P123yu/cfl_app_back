package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.Certificate;
import com.cfl.cfl_project.model.LogBook;
import com.cfl.cfl_project.service.CertificateService;
import com.cfl.cfl_project.service.LogBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/certificate")
@CrossOrigin("*")
@Slf4j
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @PostMapping("/upload")
    private ResponseEntity<?> upload(@RequestParam Long empId, @RequestPart MultipartFile certificateFile)throws IOException {
        log.info("empId: {}, certificateFile: {}", empId, certificateFile);
        Certificate certificate = certificateService.uploadCertificate(empId, certificateFile);
        if(certificate!=null){
            return ResponseEntity.ok(certificate);
        }
        else{
            return ResponseEntity.status(400).body("Failed to upload certificate");
        }
    }


    @GetMapping("/download/{empId}")
    private ResponseEntity<?> download(@PathVariable Long empId) throws IOException {
        List<Certificate> certificate = certificateService.downloadCertificate(empId);
        if(!certificate.isEmpty()){
            return ResponseEntity.ok(certificate);
        }
        else{
            return ResponseEntity.status(404).body("No certificate found for this employee");
        }
    }
}
