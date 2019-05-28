package com.eum.program.dto;

import java.util.Date;
import java.util.List;

import com.eum.attachFile.dto.AttachFileDTO;
import com.eum.program.type.OpenType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramDTO {
    private Long id;
    private Long kind;
    private String title;
    private Date strDate;
    private Date endDate;
    private Date strAppDate;
    private Date endAppDate;
    private String address;
    private String addressNumber;
    private String addressDetail;
    private String content;
    private OpenType openType;
    private int regNumber;
    private String option;
    private Long regId;
    private int limitNumber;
    private String deleteYN = "N";
    private Date updateData = new Date();
    private Date regDate = new Date();
    private List<AttachFileDTO> attachFile;
    private AttachFileDTO imageFile;
}
