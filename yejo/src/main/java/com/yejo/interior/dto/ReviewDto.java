package com.yejo.interior.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
	private String title, content, url;
	private List<String> tags;
	private MultipartFile image;
}
