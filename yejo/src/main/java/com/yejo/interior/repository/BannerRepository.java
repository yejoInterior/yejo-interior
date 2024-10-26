package com.yejo.interior.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yejo.interior.entity.BannerEntity;

public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
	 @Query("SELECT COALESCE(MAX(b.displayOrder), 0) FROM BannerEntity b")
	 int findMaxDisplayOrder();
	 
	 @Query("SELECT b.imagePath FROM BannerEntity b ORDER BY b.displayOrder ASC")
	 List<String> findAllImagePathsOrderByDisplayOrder();
}
