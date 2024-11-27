package com.yejo.interior.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yejo.interior.dto.PolicyDto;
import com.yejo.interior.entity.PolicyEntity;
import com.yejo.interior.repository.PolicyRepository;

@Service
public class PolicyService {
	@Autowired
	private PolicyRepository policyRepository;
	
	public ResponseEntity<String> savePolicyService(PolicyDto policyDto){
		
		try {
			PolicyEntity policyEntity = new PolicyEntity();
			policyEntity.setPolicyContent(policyDto.getPolicyContent());
			policyEntity.setGuideContent(policyDto.getGuideContent());
			
			policyRepository.deleteAll();
			policyRepository.save(policyEntity);
			
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			System.out.println(e);
			return ResponseEntity.status(801).body("개인정보처리방침 저장 실패");
		}
		
	}
	
	public PolicyEntity getPolicyContent() {
		return policyRepository.findAll().get(0);
	}
}
