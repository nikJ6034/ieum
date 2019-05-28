package com.eum.program.dto;

import com.eum.common.dto.PageDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProgramApplicationSearchDTO extends PageDTO {
    private Long programId;
    private Long id;

}
