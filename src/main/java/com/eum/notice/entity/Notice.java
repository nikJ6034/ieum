package com.eum.notice.entity;

import com.eum.attachFile.entity.AttachFile;
import com.eum.bbs.entity.Bbs;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("notice")
@Data
public class Notice extends Bbs {

	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "notice_file", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
	private List<AttachFile> attachFile;
}
