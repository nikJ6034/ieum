package com.eum.banner.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Banner {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable=false) 
	private String title;
	@Column(nullable=false) @Lob
	private String description;
	private int width;
	private int height;

	@JsonManagedReference
	@OneToMany(mappedBy = "banner")
	@OrderBy("sortNumber asc")
	private List<BannerImage> bannerImage;

}
