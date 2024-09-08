package com.cfl.cfl_project.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MentorToMenteeFeedBack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long menteeId;
    private String menteeName;
    private String mentorName;
    private String mentorEmail;

    @Column(columnDefinition = "TEXT")
    private String feedbackMessage;
    private LocalDate feedbackDate;
    private Long year;
}
