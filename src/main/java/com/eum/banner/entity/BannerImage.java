package com.eum.banner.entity;

import com.eum.attachFile.entity.AttachFile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@IdClass(BannerImageId.class)
@Getter @Setter
public class BannerImage {
    @JsonBackReference
    @Id
    @ManyToOne
    @JoinColumn(name="banner_id")
    private Banner banner;

    @Id
    @ManyToOne
    @JoinColumn(name="image_id")
    private AttachFile attachFile;

    private String title;

    private String content;

    private int sortNumber;
}
