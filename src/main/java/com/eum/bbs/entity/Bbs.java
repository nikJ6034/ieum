package com.eum.bbs.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.eum.common.entity.BaseEntity;
import com.eum.member.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="bbsType")
@Getter @Setter
public abstract class Bbs extends BaseEntity {
	@Id @GeneratedValue
	private Long id;
	
	@Column()
	private Long pid;

	@Column(nullable=false) 
	private String title;
	@Column(nullable=false) @Lob
	private String content;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="reg_id")
	private Member member;

	@Column 
	private String updateId;

}
