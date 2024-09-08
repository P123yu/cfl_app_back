package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="lateral_shift")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LateralShift {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    private Long empId;
    private String internal1;
    private String internal2;
    private String internal3;
    private String project1;
    private String project2;
    private String project3;
    @Column(columnDefinition = "Text")
    private String skillGap;
    private String backUp;
    private String backUpForWhom;
    private Long year;


}
