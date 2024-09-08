package com.cfl.cfl_project.service.impl;


import com.cfl.cfl_project.model.Certificate;
import com.cfl.cfl_project.model.UserManual;
import com.cfl.cfl_project.repository.CertificateRepository;
import com.cfl.cfl_project.repository.UserManualRepository;
import com.cfl.cfl_project.service.CertificateService;
import com.cfl.cfl_project.service.UserManualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class UserManualServiceImpl implements UserManualService {

    @Autowired
    private UserManualRepository userManualRepository;


    @Override
    public UserManual uploadUserManual(Long Id, MultipartFile userManualFile) throws IOException {
        UserManual userManual = new UserManual();
        userManual.setId(Id);
        userManual.setUserManualFileName(userManualFile.getOriginalFilename());
        userManual.setUserManualFileData(userManualFile.getBytes());
        return userManualRepository.save(userManual);
    }

    @Override
    public UserManual downloadUserManual(Long Id) {
        if(userManualRepository.findById(Id).isPresent()){
            return userManualRepository.findById(Id).get();
        }
        return null;
    }
}
