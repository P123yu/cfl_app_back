package com.cfl.cfl_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CflProbationConfirmationCflTableDTO {

    private Long employeeCode;

    private String cflName;
    private String cflEmail;
    private String reportingManagerName;
    private String reportingManagerEmail;
    private String cflLocation;
    private String cflDepartment;
    private String cflSubDepartment;


    private String reportingManagerSignature;
    private String buHeadName;
    private String buHeadSignature;
    private String hrRepresentativeName;
    private String hrRepresentativeSignature;


    private String designation;
    private String location;
    private String department;
    private String project;
    private String dateOfJoining;
    private String dateOfConfirmation;

    private String performanceStandard1;
    private String qualityOfWork1;
    private String subjectKnowledge1;
    private String initiativeAndWillingness1;
    private String Attendance1;
    private String teamWork1;
    private String organizingAndTeamManagement1;
    private String attitudeTowardsWork1;
    private String wellVersedWithCompanyPolicies1;
    private String companyCodeOfConduct1;

    private String remark3;

    private String performanceStandard2;
    private String qualityOfWork2;
    private String subjectKnowledge2;
    private String initiativeAndWillingness2;
    private String Attendance2;
    private String teamWork2;
    private String organizingAndTeamManagement2;
    private String attitudeTowardsWork2;
    private String wellVersedWithCompanyPolicies2;
    private String companyCodeOfConduct2;

    private String remark6;

    private String confirmation;

    private String status;
}
