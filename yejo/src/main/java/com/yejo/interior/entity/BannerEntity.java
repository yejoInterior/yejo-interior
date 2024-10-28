package com.yejo.interior.entity;

import java.io.Serializable;

import com.yejo.interior.dto.BannerDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BannerEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int displayOrder;
	private String isVisible;
	private String title;
	private String imagePath;
	private String imageName;
	
	public BannerEntity(BannerDto bannerDto, String imagePath) {

        this.isVisible = "Y";
        this.title = bannerDto.getBannerTitle();
        this.imagePath = imagePath;
        this.imageName = bannerDto.getBannerImage().getOriginalFilename();
    }
	
}
