package com.eum.program.dto;

import com.eum.common.dto.PageDTO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProgramSearchDTO extends PageDTO {
    private Long id;
    private Long kind;
    private String title;
}
