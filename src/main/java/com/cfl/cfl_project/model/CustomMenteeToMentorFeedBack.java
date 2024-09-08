package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomMenteeToMentorFeedBack {
    private Long menteeId;
    private String menteeName;
    private String feedbackMessage;
    private LocalDate feedbackDate;
    private String mentorName;
}
