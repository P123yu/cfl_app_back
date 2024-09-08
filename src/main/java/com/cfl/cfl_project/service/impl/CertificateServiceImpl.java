package com.cfl.cfl_project.service.impl;


import com.cfl.cfl_project.model.Certificate;
import com.cfl.cfl_project.repository.CertificateRepository;
import com.cfl.cfl_project.service.CertificateService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;


    @Override
    public Certificate uploadCertificate(Long empId, MultipartFile certificateFile) throws IOException {
        Certificate certificate =new Certificate();
        certificate.setEmpId(empId);
        LocalDate date = LocalDate.now();
        certificate.setDate(date);
        LocalTime time= LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        certificate.setTime(time);
        certificate.setCertificateFileName(certificateFile.getOriginalFilename());
        certificate.setCertificateFileData(certificateFile.getBytes());
        return certificateRepository.save(certificate);
    }

    @Override
    public List<Certificate> downloadCertificate(Long empId) {
        return certificateRepository.findByEmpId(empId);
    }
}
