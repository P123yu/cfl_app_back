package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cfl_roles")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CflRoles {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
//    @Lob
    @Column(columnDefinition = "TEXT")
    private String roleName;
    private Long year;

}
