package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.Certificate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface CertificateService {
    Certificate uploadCertificate(Long empId, MultipartFile certificateFile) throws IOException;
    List<Certificate> downloadCertificate(Long empId);
}
