package com.yejo.interior.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yejo.interior.entity.AdminPasswordEntity;
import com.yejo.interior.repository.AdminPasswordRepository;

@Service
public class AdminPasswordService {

	@Autowired
	private AdminPasswordRepository adminPasswordRepository;
	

    // 비밀번호 비교
    public boolean checkPassword(String password) {
        // 관리자 비밀번호는 하나만 존재하므로 ID가 1인 레코드만 찾음
        AdminPasswordEntity adminPassword = adminPasswordRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("관리자 비밀번호가 존재하지 않습니다."));

        // DB에서 가져온 비밀번호와 클라이언트가 보낸 비밀번호 비교
        return adminPassword.getPassword().equals(password);
    }
	
    public String changePassword(String newPassword) {
        // 관리자 비밀번호는 하나만 존재하므로 ID 1로 조회
        AdminPasswordEntity adminPassword = adminPasswordRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("관리자 비밀번호가 존재하지 않습니다."));
        
        // 비밀번호 변경
        adminPassword.setPassword(newPassword);
        adminPasswordRepository.save(adminPassword); // 업데이트

        return "비밀번호가 성공적으로 변경되었습니다.";
    }
}
