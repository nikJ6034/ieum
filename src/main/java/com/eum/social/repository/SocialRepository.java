package com.eum.social.repository;

import com.eum.social.entity.Social;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialRepository extends JpaRepository<Social,Long> {
    Optional<Social> findByKindAndSocialKey(String kind, String socialKey);
}
