package com.eum.banner.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

	@JsonManagedReference
	@OneToMany(mappedBy = "banner")
	@OrderBy("sortNumber asc")
	private List<BannerImage> bannerImage;

}
