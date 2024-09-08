package com.cfl.cfl_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProbationTrackerCflTableDTO {

    private Long empId;

    private String cflName;
    private String cflEmail;
    private String reportingManagerName;
    private String reportingManagerEmail;
    private String cflLocation;
    private String cflDepartment;
    private String cflSubDepartment;

    private Boolean probationInitiatedFromHrToManager;
    private Boolean responseSendByManagerToCfl;
    private Boolean responseSendByManagerToHr;
}
