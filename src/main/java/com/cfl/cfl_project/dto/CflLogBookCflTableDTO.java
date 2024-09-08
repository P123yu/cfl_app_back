package com.cfl.cfl_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CflLogBookCflTableDTO {
    private Long empId;

    private String cflName;
    private String cflEmail;
    private String reportingManagerName;
    private String reportingManagerEmail;
    private String cflLocation;
    private String cflDepartment;
    private String cflSubDepartment;

    private LocalDate submittedDate;
    private LocalTime submittedTime;
    private String logBookName;
}


