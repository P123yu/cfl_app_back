package com.cfl.cfl_project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PerformanceDTO {

    @Column(name = "emp_id", nullable = true)
    private Long empId;

    @Column(name = "cfl_first_name", nullable = true)
    private String cflFirstName;

    @Column(name = "cfl_last_name", nullable = true)
    private String cflLastName;

    @Column(name = "cfl_department", nullable = true)
    private String cflDepartment;

    @Column(name = "cfl_email", nullable = true)
    private String cflEmail;

    @Column(name = "file_name", nullable = true)
    private String fileName;

    @Column(name = "file_data", columnDefinition = "BYTEA", nullable = true)
    private byte[] fileData;

    private String talentLevel;
    private String potentialLevel;
    private String performanceLevel;

}
