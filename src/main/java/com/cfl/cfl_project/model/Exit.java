package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Exit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "emp_id", nullable = true)
    private Long empId;

    @Column(name = "cfl_first_name", nullable = true)
    private String cflFirstName;

    @Column(name = "cfl_last_name", nullable = true)
    private String cflLastName;

    @Column(name = "cfl_email", nullable = true)
    private String cflEmail;

    @Column(name = "joining_date", nullable = true)
    private String joiningDate;


    @Column(name = "cfl_designation", nullable = true)
    private String cflDesignation;

    @Column(name = "cfl_department", nullable = true)
    private String cflDepartment;

    @Column(name = "reporting_manager", nullable = true)
    private String reportingManager;

    @Column(name = "manager_email", nullable = true)
    private String reportingManagerMail;

    @Column(name = "exit_date", nullable = true)
    private String exitDate;

    @Column(name = "relieving_date", nullable = true)
    private String relievingDate;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Column(columnDefinition = "TEXT")
    private String declineReason;

    @Column(name="extended_date")
    private String extendedDate;

    @Column(columnDefinition = "TEXT")
    private String extendedReason;

    @Column(name="status")
    private String status;

    @Column(name="accept_status")
    private String acceptStatus;
}
