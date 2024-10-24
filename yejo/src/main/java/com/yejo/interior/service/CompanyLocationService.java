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
    
    // 위치 정보 업데이트
    public CompanyLocationEntity updateLocation(CompanyLocationEntity updatedLocation) {
        CompanyLocationEntity existingLocation = locationRepository.findById(updatedLocation.getId())
                .orElseThrow(() -> new RuntimeException("위치 정보를 찾을 수 없습니다."));

        // 기존 데이터를 업데이트
        existingLocation.setAddress(updatedLocation.getAddress());
        existingLocation.setTel(updatedLocation.getTel());
        existingLocation.setSafeTel(updatedLocation.getSafeTel());
        existingLocation.setEmail(updatedLocation.getEmail());
        existingLocation.setNaverMapUrl(updatedLocation.getNaverMapUrl());
        existingLocation.setKakaoMapUrl(updatedLocation.getKakaoMapUrl());
        existingLocation.setLatitude(updatedLocation.getLatitude());
        existingLocation.setLongitude(updatedLocation.getLongitude());

        // 업데이트된 위치 정보를 저장
        return locationRepository.save(existingLocation);
    }
}
