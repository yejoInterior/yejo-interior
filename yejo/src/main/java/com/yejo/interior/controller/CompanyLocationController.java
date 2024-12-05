package com.yejo.interior.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yejo.interior.entity.CompanyLocationEntity;
import com.yejo.interior.service.CompanyLocationService;

@RestController
@RequestMapping("/api/location")
public class CompanyLocationController {

    @Autowired
    private CompanyLocationService locationService;
    
    @Value("${kakao.javaScriptKey}")
    private String jsKey;
    
    // 위치 정보 저장
    @PostMapping
    public ResponseEntity<CompanyLocationEntity> saveLocation(@ModelAttribute CompanyLocationEntity location) {
        CompanyLocationEntity savedLocation = locationService.saveLocation(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    // 위치 정보 조회
    @GetMapping
    public ResponseEntity<Map<String, Object>> getLocation() {
        CompanyLocationEntity location = locationService.getLocation();

        Map<String, Object> response = new HashMap<>();
        response.put("location", location);
        response.put("jsKey", jsKey);

        return ResponseEntity.ok(response);
    } 
    
    // 위치 정보 업데이트 (POST)
    @PostMapping("/update")
    public ResponseEntity<CompanyLocationEntity> updateLocation(@ModelAttribute CompanyLocationEntity location) {
        CompanyLocationEntity updatedLocation = locationService.updateLocation(location);
        return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    }
    
    
    
}
