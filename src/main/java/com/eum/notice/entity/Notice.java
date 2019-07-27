package com.eum.notice.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.eum.attachFile.entity.AttachFile;
import com.eum.bbs.entity.Bbs;

import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("notice")
@Getter @Setter
public class Notice extends Bbs {

	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "notice_file", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
	private List<AttachFile> attachFile;
}
