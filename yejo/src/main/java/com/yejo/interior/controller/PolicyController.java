package com.yejo.interior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yejo.interior.dto.PolicyDto;
import com.yejo.interior.service.PolicyService;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {
	
	@Autowired
	private PolicyService policyService;
	
	@PatchMapping
	public ResponseEntity<String> savePolicy(@RequestBody PolicyDto policyDto){
		return policyService.savePolicyService(policyDto);
	}
}
