package com.cfl.cfl_project.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="goal_setting_tracker")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoalSettingTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cflId;
    private Boolean goalInitiatedFromHrToManager;
    private Boolean responseSendByManagerToCfl;
    private Boolean responseSendByManagerToHr;
    private String quarter;

    private String cflName;
    private Long year;
}
