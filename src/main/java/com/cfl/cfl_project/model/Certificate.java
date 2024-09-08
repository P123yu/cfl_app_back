package com.cfl.cfl_project.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="certification_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    private Long empId;


    private LocalDate date;

    private LocalTime time;
    @Column(name = "file_name")
    private String certificateFileName;
//    @Lob
//    @Column(name = "file_data", columnDefinition = "LONGBLOB")
//    private byte[] certificateFileData;


    @Column(name = "file_data", columnDefinition = "BYTEA")
    private byte[] certificateFileData;
}
