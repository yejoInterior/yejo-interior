package com.yejo.interior.dto;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
	private String title, content, tag;
	private File img;
}
