package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.ManagerToCflFeedBack;
import com.cfl.cfl_project.model.MentorToMenteeFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorToMenteeFeedBackRepository extends JpaRepository<MentorToMenteeFeedBack,Long> {
    List<MentorToMenteeFeedBack> findByYear(Long year);
}
