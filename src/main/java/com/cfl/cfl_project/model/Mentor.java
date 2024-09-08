package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="mentor_table")

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mentor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private String empId;
    private Long mentorId;
    private String mentorName;
    private String mentorEmail;
    private String mentorDepartment;
    private String mentorLocation;
    private String mentorDesignation;
    private String mentorFileName;

//    @Lob
//    @Column(name = "mentor_file_data", columnDefinition = "LONGBLOB", nullable = false)
//    private byte[] mentorFileData;


    @Column(name = "mentor_file_data", columnDefinition = "BYTEA")
    private byte[] mentorFileData;

    @Column(name = "otp")
    private String otp;

    private String mentorScreenTime;


}
