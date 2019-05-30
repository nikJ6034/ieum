package com.eum.program.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ProgramApplication {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Program program;

    @Column
    private Long memberId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String tel;
    @Column
    private String etc;
    @Column
    private String option;
    @Column
    private String deleteYN = "N";
}
