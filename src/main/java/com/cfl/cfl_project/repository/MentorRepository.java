package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorRepository extends JpaRepository<Mentor,Long> {
    Boolean existsByMentorEmail(String mentorEmail);
    Mentor findByMentorEmail(String mentorEmail);
    Mentor findByMentorId(Long mentorId);
//    Mentor findByEmpId(Long empId);


}
