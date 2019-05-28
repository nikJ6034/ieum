package com.eum.social.entity;

import com.eum.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Social {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String kind;
    @Column(length = 100, nullable = false)
    private String socialKey;
    @ManyToOne
    private Member member;

}
