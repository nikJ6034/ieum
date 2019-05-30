package com.eum.program.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.eum.attachFile.entity.AttachFile;
import com.eum.common.entity.BaseEntity;
import com.eum.program.type.OpenType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Program extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long kind;

    @Column(nullable = false)
    private String title;

    @Column
    private int limitNumber;

    @Column(nullable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date strDate;

    @Column(nullable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(nullable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date strAppDate;

    @Column(nullable = false)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endAppDate;

    /*@Column
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date strTime;

    @Column
    @DateTimeFormat(pattern = "HH:mm")
    @Temporal(TemporalType.TIME)
    private Date endTime;*/

    @Column
    private String address;

    @Column
    private String addressNumber;

    @Column
    private String addressDetail;

    @Column
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OpenType openType;

    @Column
    private int regNumber;

    @Column
    private String option;

    @Column
    private Long regId;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "program_file", joinColumns = @JoinColumn(name = "program_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
    private List<AttachFile> attachFile;

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private AttachFile imageFile;

}
