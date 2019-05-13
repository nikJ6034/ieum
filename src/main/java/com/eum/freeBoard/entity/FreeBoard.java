package com.eum.freeBoard.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.eum.bbs.entity.Bbs;

import lombok.Data;

@Entity
@Data
public class FreeBoard {
	
	@Id
	private long id;
	
	@MapsId
	@OneToOne
	@JoinColumn(name="id")
	private Bbs bbs;
}
