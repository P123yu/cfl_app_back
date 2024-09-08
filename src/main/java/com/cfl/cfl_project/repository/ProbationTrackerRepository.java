package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.ProbationTracker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProbationTrackerRepository extends JpaRepository<ProbationTracker,Long> {
    ProbationTracker findByCflId(Long cflId);
    boolean existsByCflId(Long cflId);
}
