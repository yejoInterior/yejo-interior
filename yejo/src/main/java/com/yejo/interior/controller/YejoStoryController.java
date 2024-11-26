package com.yejo.interior.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yejo.interior.entity.YejoStoryEntity;
import com.yejo.interior.service.YejoStoryService;

@RestController
@RequestMapping("/api/yejostory")
public class YejoStoryController {
	@Autowired
	private YejoStoryService yejoStoryService;
	
	@PostMapping("/save")
	public ResponseEntity<String> saveIntroduction(@RequestParam("text") String text,
	                                               @RequestParam(value = "image", required = false) MultipartFile image) {
	    try {
	        // 기존 엔티티를 가져옴
	        YejoStoryEntity existingStory = yejoStoryService.getIntroduction();

	        // 기존 엔티티가 있으면 업데이트, 없으면 새로 생성
	        if (existingStory != null) {
	            yejoStoryService.updateIntroduction(existingStory, text, image);
	        } else {
	            yejoStoryService.saveIntroduction(text, image);
	        }

	        return new ResponseEntity<>("저장 성공", HttpStatus.OK);
	    } catch (IOException e) {
	        return new ResponseEntity<>("이미지 처리 오류", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/get")
	public ResponseEntity<YejoStoryEntity> getIntroduction() {
		return new ResponseEntity<>(yejoStoryService.getIntroduction(), HttpStatus.OK);
	}
}
