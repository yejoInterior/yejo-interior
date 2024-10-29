package com.yejo.interior.dto;

import lombok.Data;

@Data
public class EstimateDto {
	private Long id;
	private String constructionStartDate;
	private String moveInDate;
	private String spaceType;
	private String name;
	private String tel;
	private String status;
}
