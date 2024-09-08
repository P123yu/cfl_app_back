package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="mail_history")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MailHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long empId;
    private LocalDate mailDate;
    private LocalTime mailTime;

}
