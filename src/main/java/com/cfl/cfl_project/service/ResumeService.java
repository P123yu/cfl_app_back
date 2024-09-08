package com.cfl.cfl_project.service;


import com.cfl.cfl_project.model.LogBook;
import com.cfl.cfl_project.model.Resume;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ResumeService {
    Resume uploadResume(Long empId, MultipartFile resumeFile) throws IOException;
    List<Resume> downloadResume(Long empId);
}
