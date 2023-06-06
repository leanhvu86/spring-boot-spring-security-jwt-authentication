package com.trunggame.repository;

import com.trunggame.models.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {

    List<Banner>  findBannerByStatus(Banner.Status status);
}
