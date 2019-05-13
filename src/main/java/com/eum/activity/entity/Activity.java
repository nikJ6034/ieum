package com.eum.activity.entity;

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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Activity {
	@Id
	private Long id;

	@MapsId
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Bbs bbs;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "activity_image", joinColumns = @JoinColumn(name = "activity_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
	private List<AttachFile> attachImage;
}
