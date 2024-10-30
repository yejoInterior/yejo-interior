package com.yejo.interior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yejo.interior.dto.PopupDto;
import com.yejo.interior.service.PopupService;

@RestController
@RequestMapping("/api/popup")
public class PopupController {
	@Autowired
	private PopupService popupService;
	
	@PostMapping
	public ResponseEntity<String> updatePopup(@ModelAttribute PopupDto popupDto){
		return popupService.updatePopupService(popupDto);
	}
	
	@PatchMapping
	public ResponseEntity<String> visiblePopup(){
		return popupService.visiblePopupService();
	}
	
	
}
