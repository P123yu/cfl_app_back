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

public class CompleteCflTableDTO {


    private Long empId;

    private String cflName;
    private String cflEmail;
    private String reportingManagerName;
    private String reportingManagerEmail;
    private String cflLocation;
    private String cflDepartment;
    private String cflSubDepartment;


    // 30 days goal setting
    private String thirtyDaysGoal;
    private String thirtyDaysDeliverable;
    private String thirtyDaysStatus;
    private String thirtyDaysWeightage;
    private String thirtyDaysQuarter;


    // 60 days goal setting
    private String sixtyDaysGoal;
    private String sixtyDaysDeliverable;
    private String sixtyDaysStatus;
    private String sixtyDaysWeightage;
    private String sixtyDaysQuarter;

    // 90 days goal setting
    private String ninetyDaysGoal;
    private String ninetyDaysDeliverable;
    private String ninetyDaysStatus;
    private String ninetyDaysWeightage;
    private String ninetyDaysQuarter;


    private String quarter;

    // logbook setting
    private LocalDate submittedDate;
    private LocalTime submittedTime;
    private String logBookName;

    // probation confirmation

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


    // cfl skill
    private String primarySkills;
    private String secondarySkills;
    private String otherSkills;


    // manager rating
    private String internalMovement1;
    private String internalMovement2;
    private String internalMovement3;
    private String project1;
    private String project2;
    private String project3;
    private String talentLevel;
    private String potentialLevel;
    private String performanceLevel;

}
