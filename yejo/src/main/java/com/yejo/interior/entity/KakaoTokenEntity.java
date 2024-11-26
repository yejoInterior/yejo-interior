package com.yejo.interior.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class KakaoTokenEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String accessToken;
	private int accessTokenExpires;
	private String refreshToken;
	private int refreshtokenExpires;
	
	@CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
	
	@PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
	
	public KakaoTokenEntity(Map<String, Object> body){
		this.accessToken = (String) body.get("access_token");
		this.accessTokenExpires = (int) body.get("expires_in");
		this.refreshToken = (String) body.get("refresh_token");
		this.refreshtokenExpires = (int) body.get("refresh_token_expires_in");
	}
}
