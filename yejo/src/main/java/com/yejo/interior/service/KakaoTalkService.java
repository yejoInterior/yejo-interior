package com.yejo.interior.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoTalkService {

	public void sendKakaoMessage() {
		String url = "https://kakaoapi.alimtalk.com/v1/messages";
	    String apiKey = "YOUR_KAKAO_API_KEY";  //키 발급 받아서 넣기
	    String recipientPhoneNumber = "01046119794";
	    String templateCode = "YOUR_TEMPLATE_CODE"; // 템플릿 코드 발급 받아서 넣기

	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", "KakaoAK " + apiKey);

	    Map<String, Object> body = new HashMap<>();
	    body.put("template_code", templateCode);
	    body.put("recipient", recipientPhoneNumber);

	    HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
	    restTemplate.postForEntity(url, request, String.class);
	}
}
