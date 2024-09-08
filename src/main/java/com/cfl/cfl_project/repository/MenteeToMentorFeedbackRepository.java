package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.ManagerToCflFeedBack;
import com.cfl.cfl_project.model.MenteeToMentorFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MenteeToMentorFeedbackRepository extends JpaRepository<MenteeToMentorFeedBack,Long> {
    List<MenteeToMentorFeedBack> findByYear(Long year);
}
