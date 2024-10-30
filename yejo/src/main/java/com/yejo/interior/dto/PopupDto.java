package com.yejo.interior.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PopupDto {
	private String link;
	private MultipartFile file;
}
