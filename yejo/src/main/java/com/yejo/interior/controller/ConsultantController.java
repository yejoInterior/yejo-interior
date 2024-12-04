package com.yejo.interior.controller;

import java.net.MalformedURLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yejo.interior.dto.ConsultantDto;
import com.yejo.interior.entity.ConsultantEntity;
import com.yejo.interior.service.ConsultantService;
import com.yejo.interior.utility.FileUtility;

@RestController
@RequestMapping("/api/consultant")
public class ConsultantController {
	@Autowired
	private ConsultantService consultantService;
	@Autowired
	private FileUtility fileUtility;
	
	@GetMapping("/{id}")
	public Optional<ConsultantEntity> getEstimate(@PathVariable long id) {
		Optional<ConsultantEntity> estimate = consultantService.getEstimate(id);
		return estimate;
	}
	
	@PostMapping
	public ResponseEntity<String> createEstimate(@ModelAttribute ConsultantDto consultantDto){
		return consultantService.createEstimateService(consultantDto);
	}
	
	@PostMapping("/file")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<UrlResource> downloadFile(@RequestBody String id) throws MalformedURLException{
		return fileUtility.download(id); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEstimate(@PathVariable long id){
		return consultantService.deleteEstimateService(id);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<String> changeStatus(@PathVariable long id){
		return consultantService.changeStatusService(id);
	}
}
