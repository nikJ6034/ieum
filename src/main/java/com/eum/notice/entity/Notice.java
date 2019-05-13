package com.eum.notice.entity;

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
public class Notice {
	@Id
	private Long id;

	@MapsId
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Bbs bbs;

	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "notice_file", joinColumns = @JoinColumn(name = "notice_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
	private List<AttachFile> attachFile;
}
