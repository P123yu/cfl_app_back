package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.GoalSettingTracker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalSettingTrackerRepository extends JpaRepository<GoalSettingTracker,Long> {
    GoalSettingTracker findByCflIdAndQuarter(Long cflId,String quarter);

    List<GoalSettingTracker> findByQuarterAndYear(String quarter,Long year);
}
