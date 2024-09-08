package com.cfl.cfl_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// DTO for Manager Rating CFL Table
public class ManagerRatingCflTableDTO {
    private Long empId;

    private String cflName;
    private String cflEmail;
    private String reportingManagerName;
    private String reportingManagerEmail;
    private String cflLocation;
    private String cflDepartment;
    private String cflSubDepartment;

    private String internalMovement1;
    private String internalMovement2;
    private String internalMovement3;
    private String project1;
    private String project2;
    private String project3;
    private String talentLevel;
    private String potentialLevel;
    private String performanceLevel;
    private String annual;
}
