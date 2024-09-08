package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="probation_tracker")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProbationTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cflId;
    private Boolean probationInitiatedFromHrToManager;
    private Boolean responseSendByManagerToCfl;
    private Boolean responseSendByManagerToHr;
}
