package com.yejo.interior.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "admin_password")
public class AdminPasswordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 관리자는 하나의 레코드만 있으므로 id는 1번만 사용

    @Column(nullable = false, length = 255)
    private String password; // 패스워드 필드

}

