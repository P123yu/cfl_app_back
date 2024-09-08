package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name="refresh")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refresh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private String refreshToken;
    private Instant expiry;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private  Register register;

}