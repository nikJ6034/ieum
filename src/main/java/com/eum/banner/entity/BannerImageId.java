package com.eum.banner.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter @Getter
public class BannerImageId implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long banner;
    private Long attachFile;

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
        BannerImageId that = (BannerImageId) o;
        return banner==that.banner &&
                attachFile==that.attachFile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(banner, attachFile);
    }
}
