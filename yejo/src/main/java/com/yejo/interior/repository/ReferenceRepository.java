package com.yejo.interior.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yejo.interior.entity.FloorPlanEntity;
import com.yejo.interior.entity.ReferenceEntity;

public interface ReferenceRepository extends JpaRepository<ReferenceEntity, Long>{
	List<ReferenceEntity> findByConsultantId(Long consultantId);
	void deleteByConsultantId(Long consultantId);


	@Query("SELECT f.storagePath FROM ReferenceEntity f WHERE f.consultant.id = :consultantId")
    List<String> findStoragePathsByConsultantId(@Param("consultantId") Long consultantId);
}

