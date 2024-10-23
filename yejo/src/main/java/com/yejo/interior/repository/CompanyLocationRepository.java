package com.yejo.interior.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yejo.interior.entity.LocationEntity;

@Repository
public interface CompanyLocationRepository extends JpaRepository<LocationEntity, Long> {
    // JpaRepository에서 기본적으로 제공하는 메서드 외에 추가로 필요한 쿼리 메서드를 정의할 수 있습니다.
}
