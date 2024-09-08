package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.UserManual;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UserManualService {
    UserManual uploadUserManual(Long Id, MultipartFile userManualFile) throws IOException;
    UserManual downloadUserManual(Long Id);
}
