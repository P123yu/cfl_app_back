package com.cfl.cfl_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CflGoalSettingCflTableDTO {

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

}
