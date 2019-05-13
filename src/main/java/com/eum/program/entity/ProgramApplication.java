package com.eum.program.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class ProgramApplication {

    @Id @GeneratedValue
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
