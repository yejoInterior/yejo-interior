package com.yejo.interior.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yejo.interior.dto.ConsultantDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ConsultantEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String tel;
	private String email;
	private String inflow;
	private String passwd;
	private int post;
	private String address1;
	private String address2;
	private String areaSize;
    private int roomCount;
    private int bathCount;
    private Date constructionStartDate;
    private Date moveInDate;
    private String spaceType;
    private String budget;
    private String floorPlanFileIdentifier;
    private String referenceFileIdentifier;
    
    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL, orphanRemoval = true) // 양방향 관계 설정
    @JsonManagedReference
    private List<FloorPlanEntity> floorPlanFiles;  // floorPlanFile에 해당하는 파일 리스트
    
    @OneToMany(mappedBy = "consultant", cascade = CascadeType.ALL, orphanRemoval = true) // 양방향 관계 설정
    @JsonManagedReference
    private List<ReferenceEntity> referenceFiles;   // referenceFile에 해당하는 파일 리스트
  
    private String referenceUrl;
    private String additionalInquiries; 
    private String status;
    
    @CreationTimestamp
    @Column(updatable = false) // 처음 생성 시에만 값이 설정됨
    private LocalDateTime createdAt;
	
    public ConsultantEntity(ConsultantDto consultantDto) {
        this.name = consultantDto.getName();
        this.tel = consultantDto.getTel();
        this.email = consultantDto.getEmail();
        this.inflow = consultantDto.getInflow();
        this.passwd = consultantDto.getPasswd();
        this.post = consultantDto.getPost();
        this.address1 = consultantDto.getAddress1();
        this.address2 = consultantDto.getAddress2();
        this.areaSize = consultantDto.getAreaSize();
        this.roomCount = consultantDto.getRoomCount();
        this.bathCount = consultantDto.getBathCount();
        this.constructionStartDate = consultantDto.getConstructionStartDate();
        this.moveInDate = consultantDto.getMoveInDate();
        this.spaceType = consultantDto.getSpaceType();
        this.budget = consultantDto.getBudget();
        this.referenceUrl = consultantDto.getReferenceUrl();
        this.additionalInquiries = consultantDto.getAdditionalInquiries();
        this.status="진행중";
    }

}
