package com.yejo.interior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yejo.interior.entity.LocationEntity;
import com.yejo.interior.service.CompanyLocationService;

@RestController
@RequestMapping("/api/location")
public class CompanyLocationController {

    @Autowired
    private CompanyLocationService locationService;

    // 위치 정보 저장
    @PostMapping
    public ResponseEntity<LocationEntity> saveLocation(@ModelAttribute LocationEntity location) {
        LocationEntity savedLocation = locationService.saveLocation(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    // 위치 정보 조회
    @GetMapping
    public ResponseEntity<LocationEntity> getLocation() {
        LocationEntity location = locationService.getLocation();
        return new ResponseEntity<>(location, HttpStatus.OK);
    }
    
    // 위치 정보 업데이트 (POST)
    @PostMapping("/update")
    public ResponseEntity<LocationEntity> updateLocation(@ModelAttribute LocationEntity location) {
        LocationEntity updatedLocation = locationService.updateLocation(location);
        return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    }
    
    
}
