package com.cfl.cfl_project.service;

import com.cfl.cfl_project.dto.CflProbationConfirmationCflTableDTO;
import com.cfl.cfl_project.model.ProbationConfirmation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProbationConfirmationService {

    ProbationConfirmation create(ProbationConfirmation probationConfirmation);
    ProbationConfirmation getByEmployeeCode(Long empId);
    List<CflProbationConfirmationCflTableDTO> cflProbationConfirmationCflTableDTOs();
    List<ProbationConfirmation>findAllProbationStatusByYear(Long year);

    ProbationConfirmation editProbationConfirmation(Long empId);
}
