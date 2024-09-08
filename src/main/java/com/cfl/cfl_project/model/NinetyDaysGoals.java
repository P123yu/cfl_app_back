package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class NinetyDaysGoals {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    private Long empId;
    private Long year;
    @Column(columnDefinition = "Text")
    private String goal;
    @Column(columnDefinition = "Text")
    private String deliverable;
    private String status;
    private String weightage;
    private String quarter;
}
