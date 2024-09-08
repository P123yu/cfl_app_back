package com.cfl.cfl_project.repository;
import java.util.List;

import com.cfl.cfl_project.model.ManagerRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRatingRepository extends JpaRepository<ManagerRating,Long> {
//    ManagerRating findByEmpIdAndQuarter(Long empId,String quarter);



    List<ManagerRating> findByEmpIdAndYear(Long empId,Long year);
    ManagerRating findByEmpId(Long empId);
//    List<ManagerRating> findByTalentLevelAndYearAndQuarter(String talentLevel,Long year,String quarter);

    List<ManagerRating> findByTalentLevelAndYear(String talentLevel,Long year);

}
