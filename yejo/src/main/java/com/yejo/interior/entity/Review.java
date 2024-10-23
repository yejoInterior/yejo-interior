package com.yejo.interior.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 255)
    private String tag;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 255)
    private String url;

    @Column(name = "image_path", length = 255)
    private String imagePath;
    
    @Transient // 이 필드는 DB에 저장되지 않도록 설정
    private List<String> tagList;

}

