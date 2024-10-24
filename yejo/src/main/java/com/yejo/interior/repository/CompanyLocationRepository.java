package com.yejo.interior.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yejo.interior.Entity.CompanyLocationEntity;

public interface CompanyLocationRepository extends JpaRepository<CompanyLocationEntity, Long> {
    // 추가적인 쿼리 메소드가 필요하다면 여기에 정의
}

