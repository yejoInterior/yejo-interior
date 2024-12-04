package com.yejo.interior.service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yejo.interior.dto.ConsultantDto;
import com.yejo.interior.entity.ConsultantEntity;
import com.yejo.interior.entity.FloorPlanEntity;
import com.yejo.interior.entity.ReferenceEntity;
import com.yejo.interior.repository.ConsultantRepository;
import com.yejo.interior.repository.FloorPlanRepository;
import com.yejo.interior.repository.ReferenceRepository;
import com.yejo.interior.utility.FileUtility;

import ch.qos.logback.core.util.FileUtil;
import jakarta.transaction.Transactional;

@Service
public class ConsultantService {
	@Autowired
	private FileUtility fileUtility;
	@Autowired
	private ConsultantRepository consultantRepository;
	@Autowired 
	private FloorPlanRepository floorPlanRepository;
	@Autowired
	private ReferenceRepository referenceRepository;
	@Autowired
	private KakaoTalkService kakaoTalkService;
	
	public List<ConsultantEntity> getAllEstimate(){
		return consultantRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
	}
	
	public Optional<ConsultantEntity> getEstimate(long id) {
		return consultantRepository.findById(id);
	}
	
	@Transactional  // 트랜잭션 관리
    public ResponseEntity<String> createEstimateService(ConsultantDto consultantDto) {
        String fileIdentifier1 = UUID.randomUUID().toString();
        String fileIdentifier2 = UUID.randomUUID().toString();
        
        // FileEntity 리스트 생성
        List<FloorPlanEntity> floorPlanFiles = new ArrayList<>();
        List<ReferenceEntity> referenceFiles = new ArrayList<>();
        // 평면도 파일 처리
        for (var file : consultantDto.getFloorPlanFile()) {
            Map<String, Object> fileUploadResult = fileUtility.uploadFile(file, "consultant/floorplan");
            if (!(boolean) fileUploadResult.get("success")) {
                return ResponseEntity.status(705).body("견적 파일 저장 실패");
            } else {
                FloorPlanEntity floorPlanEntity = new FloorPlanEntity((String) fileUploadResult.get("filePath"), fileIdentifier1, file.getOriginalFilename());
                floorPlanFiles.add(floorPlanEntity);  // 리스트에 추가만 하고 저장은 나중에
            }
        }

        
        // 참조 파일 처리
        if (consultantDto.getReferenceFile() != null && consultantDto.getReferenceFile().length > 0) {
        	for (var file : consultantDto.getReferenceFile()) {
                Map<String, Object> fileUploadResult = fileUtility.uploadFile(file, "consultant/reference");
                
                if (!(boolean) fileUploadResult.get("success")) {
                    return ResponseEntity.status(705).body("견적 파일 저장 실패");
                } else {
                    ReferenceEntity referenceEntity = new ReferenceEntity((String) fileUploadResult.get("filePath"), fileIdentifier2, file.getOriginalFilename());
                    referenceFiles.add(referenceEntity);  // 리스트에 추가만 하고 저장은 나중에
                }
            }
        }
        
        
        // ConsultantEntity 생성 및 파일 리스트 연결
        ConsultantEntity consultantEntity = new ConsultantEntity(consultantDto);
        consultantEntity.setFloorPlanFiles(floorPlanFiles);
        consultantEntity.setReferenceFiles(referenceFiles);
        
        // 각 FileEntity와 ConsultantEntity 간의 관계 설정
        for (FloorPlanEntity file : floorPlanFiles) {
            file.setConsultant(consultantEntity);
        }
        for (ReferenceEntity file : referenceFiles) {
            file.setConsultant(consultantEntity);
        }

        // ConsultantEntity 저장
        consultantRepository.save(consultantEntity);

        // 모든 파일을 한 번에 저장
        floorPlanRepository.saveAll(floorPlanFiles);
        referenceRepository.saveAll(referenceFiles);

        //카카오톡 알림톡 전송
        kakaoTalkService.sendKakaoMessage(consultantDto.getBudget());
        return ResponseEntity.ok().build();
    }
	
	public List<ResponseEntity<UrlResource>> downloadFileService(long id) throws MalformedURLException {
	    List<FloorPlanEntity> files = floorPlanRepository.findByConsultantId(id);
	    
	    List<ResponseEntity<UrlResource>> result = new ArrayList<>();

	    for (FloorPlanEntity file : files) {
	        ResponseEntity<UrlResource> response = fileUtility.download(file.getStoragePath());
	        result.add(response);
	    }
	    
	    return result;
	}
	
	public ResponseEntity<String> deleteEstimateService(long id){
		
		try{
			
			List<String> floorFilePaths = floorPlanRepository.findStoragePathsByConsultantId(id);
			List<String> referencePaths = referenceRepository.findStoragePathsByConsultantId(id);

			if (floorFilePaths != null) {
			    for (String floorFilePath : floorFilePaths) {
			        fileUtility.deleteFileFromCDN(floorFilePath);
			    }
			}

			if (referencePaths != null) {
			    for (String referencePath : referencePaths) {
			        fileUtility.deleteFileFromCDN(referencePath);
			    }
			}
			
			consultantRepository.deleteById(id);			
			floorPlanRepository.deleteByConsultantId(id);
			referenceRepository.deleteByConsultantId(id);

			return ResponseEntity.ok().build();
		}catch(Exception e) {
			System.out.println(e);
			return ResponseEntity.status(901).body("견적 삭제 실패");
		}

	}

	public ResponseEntity<String> changeStatusService(Long id){
		try {
			consultantRepository.toggleStatusById(id);			
			return ResponseEntity.ok().build();
		}catch(Exception e) {
			return ResponseEntity.status(902).body("상태 변경 실패");
		}
	}
	
}
