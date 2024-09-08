package com.cfl.cfl_project.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Builder

public class AnnualAppraisalInfoDTO {

    private Long empId;
    private String empName;
}
