package com.cfl.cfl_project.service;

import com.cfl.cfl_project.dto.CflLogBookCflTableDTO;
import com.cfl.cfl_project.model.LogBook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface LogBookService {
    LogBook uploadLogBook(Long empId, MultipartFile LogBookFile) throws IOException;
    List<LogBook> downloadLogBook(Long empId);
    List<CflLogBookCflTableDTO> downloadLogBookReport();
}
