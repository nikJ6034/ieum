package com.eum.banner.repository;

import com.eum.banner.entity.BannerImage;
import com.eum.banner.entity.BannerImageId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerImageRepository  extends JpaRepository<BannerImage, BannerImageId> {
}
