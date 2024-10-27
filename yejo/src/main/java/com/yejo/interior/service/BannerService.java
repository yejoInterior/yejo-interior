package com.yejo.interior.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yejo.interior.dto.BannerDto;
import com.yejo.interior.entity.BannerEntity;
import com.yejo.interior.repository.BannerRepository;
import com.yejo.interior.utility.FileUtility;

@Service
public class BannerService {
	@Autowired	
	private FileUtility fileUtility;
	@Autowired
	private BannerRepository bannerRepository;
	
	public List<BannerEntity> getBanner() {
	    return bannerRepository.findAll(Sort.by(Sort.Direction.ASC, "displayOrder")); // displayOrder 기준 오름차순 정렬	    
	}
	
	public ResponseEntity<String> createBannerService(BannerDto bannerDto){
		Map<String, Object> fileUploadResult = fileUtility.uploadFile(bannerDto.getBannerImage(), "main");
		
		if((boolean)fileUploadResult.get("success")) {
			try {
				BannerEntity bannerEntity = new BannerEntity(bannerDto, (String)fileUploadResult.get("filePath"));
				bannerEntity.setDisplayOrder(bannerRepository.findMaxDisplayOrder()+1);
				bannerRepository.save(bannerEntity);
				return ResponseEntity.ok().build();
			}catch(Exception e) {
				return ResponseEntity.status(702).body("배너 저장 실패");			}
			
		}else {
			return ResponseEntity.status(701).body("파일 업로드 실패");
		}

	}
	
	public ResponseEntity<String> updateVisibleService(Long id){
		BannerEntity banner = bannerRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("배너를 찾을 수 없습니다: " + id));

        String currentVisibility = banner.getIsVisible();
        if ("Y".equals(currentVisibility)) {
            banner.setIsVisible("N");
        } else if ("N".equals(currentVisibility)) {
            banner.setIsVisible("Y");
        }

        bannerRepository.save(banner);
        return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<String> updateOrderService(List<Long> ids){
		
		try {
			for (Long id : ids) {
	            BannerEntity banner = bannerRepository.findById(id).orElseThrow();
	            banner.setDisplayOrder(0);
	            bannerRepository.save(banner);
	        }

	        for (int index = 0; index < ids.size(); index++) {
	        	Long id = ids.get(index);
	            BannerEntity banner = bannerRepository.findById(id).orElseThrow();
	            banner.setDisplayOrder(index + 1);
	            bannerRepository.save(banner);
	        }

			return ResponseEntity.ok().build();
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(704).body("배너 순서 변경 오류");
		}
	}
	
	public ResponseEntity<String> deleteBannerService(Long id){
		try{
			bannerRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			return ResponseEntity.status(703).body("배너 삭제 실패");
		}
	}
	
	//main페이지 배너 출력
	public List<String> getBannerPath(){
		return bannerRepository.findAllImagePathsOrderByDisplayOrder();
	}
}
