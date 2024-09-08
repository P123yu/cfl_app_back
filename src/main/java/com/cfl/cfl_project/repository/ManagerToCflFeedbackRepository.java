package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.ManagerToCflFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerToCflFeedbackRepository extends JpaRepository<ManagerToCflFeedBack,Long> {
    List<ManagerToCflFeedBack> findByYear(Long year);
}
