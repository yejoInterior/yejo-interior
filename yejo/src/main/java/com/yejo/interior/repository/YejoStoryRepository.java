package com.yejo.interior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.yejo.interior.entity.YejoStoryEntity;

@Repository
public interface YejoStoryRepository extends JpaRepository<YejoStoryEntity, Long> {
	YejoStoryEntity findFirstByOrderByIdDesc();
}
