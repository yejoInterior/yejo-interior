package com.yejo.interior.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yejo.interior.entity.CompanyLocationEntity;
import com.yejo.interior.repository.CompanyLocationRepository;

@Service
public class CompanyLocationService {

    @Autowired
    private CompanyLocationRepository locationRepository;

    @Autowired
    private RedisTemplate<String, CompanyLocationEntity> redisTemplate;

    private static final String LOCATION_CACHE_KEY = "companyLocation";

    // 위치 정보 저장
    public CompanyLocationEntity saveLocation(CompanyLocationEntity location) {
        CompanyLocationEntity savedLocation = locationRepository.save(location);
        redisTemplate.opsForValue().set(LOCATION_CACHE_KEY, savedLocation); // Redis에 저장
        return savedLocation;
    }

    // 위치 정보 조회
    public CompanyLocationEntity getLocation() {
        // Redis에서 먼저 조회
        CompanyLocationEntity location = redisTemplate.opsForValue().get(LOCATION_CACHE_KEY);
        if (location == null) {
            // 캐시에 없으면 DB에서 조회
            location = locationRepository.findById(1L).orElse(null);
            if (location != null) {
                redisTemplate.opsForValue().set(LOCATION_CACHE_KEY, location); // Redis에 캐싱
            }
        }
        return location;
    }
}

