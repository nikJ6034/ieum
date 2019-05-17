package com.eum.activity.dto;

import java.util.List;

import com.eum.attachFile.dto.AttachFileDTO;
import com.eum.bbs.dto.BbsDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ActivityDTO extends BbsDTO{
    private List<AttachFileDTO> attachImage;
}
