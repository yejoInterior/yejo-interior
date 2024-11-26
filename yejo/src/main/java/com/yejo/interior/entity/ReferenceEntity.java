package com.yejo.interior.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ReferenceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;               // 고유 ID (Primary Key)
    
    private String fileIdentifier;  // 파일 식별번호 (파일의 고유한 식별값)
    
    private String storagePath;     // 저장 경로 (파일이 저장된 위치)

    private String realName;
    
    @ManyToOne 
    @JsonBackReference("referenceFiles")
    @JoinColumn(name = "consultant_id") // 외래 키 설정
    private ConsultantEntity consultant;
    
    public ReferenceEntity(String path, String identifier, String fileName) {
    	this.fileIdentifier = identifier;
    	this.storagePath = path;
    	this.realName = fileName;
    }
}