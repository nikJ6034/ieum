package com.eum.role.entity;

import com.eum.api.entity.ApiRole;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {
	@Id @GeneratedValue
	private Long id;
	@Column
	private String roleName;
}
