package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="manager_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long managerId;
    private String managerName;
    private String managerEmail;
    private String managerDepartment;
    private String managerLocation;
    private String managerDesignation;
    private String managerFileName;

//    @Lob
//    @Column(name = "manager_file_data", nullable = false, columnDefinition = "LONGBLOB")
//    private byte[] managerFileData;


    @Column(name = "manager_file_data", columnDefinition = "BYTEA")
    private byte[] managerFileData;

    @Column(name = "otp")
    private String otp;

    private String managerScreenTime;


}
