package com.yejo.interior.dto;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ConsultantDto {
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
    private MultipartFile[] floorPlanFile;
    private MultipartFile[] referenceFile;
    private String referenceUrl;
    private String additionalInquiries; 
}
