package com.eum.bbs.entity;

import com.eum.common.entity.BaseEntity;
import com.eum.member.entity.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
