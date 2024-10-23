package com.yejo.interior.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yejo.interior.entity.CompanyLocationEntity;
import com.yejo.interior.repository.CompanyLocationRepository;

@Service
public class CompanyLocationService {

    @Autowired
    private CompanyLocationRepository locationRepository;

    // 위치 정보 저장
    public CompanyLocationEntity saveLocation(CompanyLocationEntity location) {
        return locationRepository.save(location); // DB에 저장
    }

    // 위치 정보 조회
    public CompanyLocationEntity getLocation() {
        return locationRepository.findById(1L).orElse(null); // DB에서 조회
    }
}
