package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProbationConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeCode;
    private String employeeName;
    private String designation;
    private String location;
    private String department;
    private String project;
    private String dateOfJoining;
    private String dateOfConfirmation;

    private String dropdown1;
    private String dropdown2;
    private String dropdown3;
    private String dropdown4;
    private String dropdown5;
    private String dropdown6;
    private String dropdown7;
    private String dropdown8;
    private String dropdown9;
    private String dropdown10;

    @Column(columnDefinition = "TEXT")
    private String remark3;

    private String dropdown11;
    private String dropdown12;
    private String dropdown13;
    private String dropdown14;
    private String dropdown15;
    private String dropdown16;
    private String dropdown17;
    private String dropdown18;
    private String dropdown19;
    private String dropdown20;

    @Column(columnDefinition = "TEXT")
    private String remark6;

    private String confirmation;

    private String reportingManagerName;
    private String reportingManagerSignature;
    private String buHeadName;
    private String buHeadSignature;
    private String hrRepresentativeName;
    private String hrRepresentativeSignature;

    private String status;

    private Long year;
}
