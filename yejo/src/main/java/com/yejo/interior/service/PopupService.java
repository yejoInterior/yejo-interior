package com.yejo.interior.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yejo.interior.dto.PopupDto;
import com.yejo.interior.entity.BannerEntity;
import com.yejo.interior.entity.PopupEntity;
import com.yejo.interior.repository.PopupRepository;
import com.yejo.interior.utility.FileUtility;

@Service
public class PopupService {
	@Autowired
	private FileUtility fileUtility;
	@Autowired
	private PopupRepository popupRepository;
	
	public  Optional<PopupEntity> getPopup() {
		return popupRepository.findById(1L);
	}
	
	public ResponseEntity<String> visiblePopupService(){
		PopupEntity popup = popupRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("팝업을 찾을 수 없습니다: "));

	    String currentVisibility = popup.getVisible();
	    if ("Y".equals(currentVisibility)) {
	    	popup.setVisible("N");
	    } else if ("N".equals(currentVisibility)) {
	    	popup.setVisible("Y");
	    }
	
	    popupRepository.save(popup);
	    return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<String> savePopupService(PopupDto popupDto){
		Map<String, Object> result = fileUtility.uploadFile(popupDto.getFile(), "popup");
		if((boolean)result.get("success")) {
			try {
				PopupEntity popupEntity = new PopupEntity();
				popupEntity.setLink(popupDto.getLink());
				popupEntity.setFilePath((String)result.get("filePath"));
				popupEntity.setFileName(popupDto.getFile().getOriginalFilename());
				popupRepository.save(popupEntity);
				return ResponseEntity.ok().build();
			}catch(Exception e) {
				return ResponseEntity.status(708).body("팝업 저장 실패");
			}
		}else {
			return ResponseEntity.status(707).body("팝업 이미지 저장 실패");
		}

	}
	
	public ResponseEntity<String> updatePopupService(PopupDto popupDto) {
	    // 항상 ID가 1인 팝업을 조회
	    Optional<PopupEntity> optionalPopupEntity = popupRepository.findById(1L);
	    
	    if (optionalPopupEntity.isPresent()) {
	        PopupEntity popupEntity = optionalPopupEntity.get();
	        String existingFilePath = popupEntity.getFilePath();
	        if (!fileUtility.deleteFileFromCDN(existingFilePath)) {
	            return ResponseEntity.status(709).body("기존 파일 삭제 실패");
	        }
	        Map<String, Object> result = fileUtility.uploadFile(popupDto.getFile(), "popup");
	        
	        if ((boolean) result.get("success")) {
	            try {
	                popupEntity.setLink(popupDto.getLink());
	                popupEntity.setFilePath((String) result.get("filePath"));
	                
	                popupRepository.save(popupEntity); // 수정된 엔티티 저장
	                return ResponseEntity.ok().build();
	            } catch (Exception e) {
	                return ResponseEntity.status(708).body("팝업 수정 실패");
	            }
	        } else {
	            return ResponseEntity.status(707).body("팝업 이미지 수정 실패");
	        }
	    } else {
	        return ResponseEntity.status(404).body("ID가 1인 팝업을 찾을 수 없습니다.");
	    }
	}

	
}
