package com.eum.attachFile.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

/**
 * AttachFile
 */
@Entity
@Getter @Setter
public class AttachFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullPath;
    @Transient
    private String savedPath;
//    @Transient
    private String resourcePath;
    private String realName;
    private String virtualName;
    private String extension;
    private String thumbnailYN = "N";
    private Long regId;
}