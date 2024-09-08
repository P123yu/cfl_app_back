package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.RewardsAndRecognition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardsAndRecognitionRepository extends JpaRepository<RewardsAndRecognition,Long> {
    RewardsAndRecognition findByRewardsPersonName(String rewardsPersonName);
    List<RewardsAndRecognition> findByRewardRecognitionType(String rewardAndRecognitionType);
}
