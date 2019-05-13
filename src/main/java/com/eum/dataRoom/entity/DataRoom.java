package com.eum.dataRoom.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.eum.attachFile.entity.AttachFile;
import com.eum.bbs.entity.Bbs;

import lombok.Data;

@Entity
@Data
public class DataRoom {
	@Id
	private long id;
	
	@MapsId
	@OneToOne
	@JoinColumn(name="id")
	private Bbs bbs;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "data_room_file", joinColumns = @JoinColumn(name = "data_room_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
	private List<AttachFile> attachFile;
}
