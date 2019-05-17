package com.eum.dataRoom.entity;

import java.util.List;

import javax.persistence.*;

import com.eum.attachFile.entity.AttachFile;
import com.eum.bbs.entity.Bbs;

import lombok.Data;

@Entity
@DiscriminatorValue("dataRoom")
@Data
public class DataRoom extends Bbs{
	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "data_room_file", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
	private List<AttachFile> attachFile;
}
