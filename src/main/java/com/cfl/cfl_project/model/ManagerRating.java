package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="manager_rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ManagerRating {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    private Long empId;
    private String talentLevel;
    private String potentialLevel;
    private String performanceLevel;
    private String annual;
    private Long year;

    @Transient
    private String empName;

}
