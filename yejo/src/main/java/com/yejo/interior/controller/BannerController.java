package com.yejo.interior.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yejo.interior.dto.BannerDto;
import com.yejo.interior.service.BannerService;

import jakarta.servlet.annotation.MultipartConfig;

@RestController
@RequestMapping("/api/banner")
public class BannerController {

	@Autowired
	private BannerService bannerService;

	@PostMapping
	public ResponseEntity<String> createBanner(@ModelAttribute BannerDto bannerDto){
		
		return bannerService.createBannerService(bannerDto);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<String> updateVisible(@PathVariable Long id){
		return bannerService.updateVisibleService(id);
	}
	
	@PatchMapping("/order")
	public ResponseEntity<String> updateOrder(@RequestBody List<Long> ids){
		return bannerService.updateOrderService(ids);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBanner(@PathVariable Long id){
		return bannerService.deleteBannerService(id);
	}
	
}
