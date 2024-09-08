package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video,Long> {
    List<Video> findByYear(String year);
}
