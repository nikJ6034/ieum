package com.eum.member.entity;

import com.eum.role.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
	
	@Id @GeneratedValue
	private long id;
	@Column
	private String memberName;
	@Column
	private String name;
	
	@Column
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String memberPassword;

	@Column
	private String email;

	@Column String mobile;
	
//	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
//	@JoinColumn(name="member_id")
//	@JsonManagedReference
//	private List<MemberRole> roles;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="member_role",
		joinColumns = @JoinColumn(name="member_id"),
		inverseJoinColumns = @JoinColumn(name="role_id")
	)
	private List<Role> roles;
}
