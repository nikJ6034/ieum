package com.eum.banner.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import com.eum.attachFile.entity.AttachFile;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Banner {
	
	@Id
	private Long id;
	@Column(nullable=false) 
	private String title;
	@Column(nullable=false) @Lob
	private String description;
	private int width;
	private int height;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "banner_image", joinColumns = @JoinColumn(name = "banner_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
	private List<AttachFile> attachImage;
}
