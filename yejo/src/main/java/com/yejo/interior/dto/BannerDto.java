package com.yejo.interior.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BannerDto {
	private String bannerTitle;
	private MultipartFile bannerImage;
	private Long id;
	private int displayOrder;
}
