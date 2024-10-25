package com.yejo.interior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yejo.interior.entity.CompanyLocationEntity;

@Repository
public interface CompanyLocationRepository extends JpaRepository<CompanyLocationEntity, Long> {
}
