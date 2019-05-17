package com.eum.activity.entity;

import java.util.List;

import javax.persistence.*;

import com.eum.attachFile.entity.AttachFile;
import com.eum.bbs.entity.Bbs;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("activity")
@Getter @Setter
public class Activity extends Bbs{
	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "activity_image", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
	private List<AttachFile> attachImage;
}
