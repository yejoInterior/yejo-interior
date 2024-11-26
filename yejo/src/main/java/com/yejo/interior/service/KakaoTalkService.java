package com.yejo.interior.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.yejo.interior.entity.KakaoTokenEntity;
import com.yejo.interior.repository.KakaoTokenRepository;

@Service
public class KakaoTalkService {
	@Autowired
	private KakaoTokenRepository kakaoTokenRepository;
	
	@Value("${kakao.restApiKey}")
	private String REST_API_KEY;
	@Value("${kakao.redirectUri}")
	private String REDIRECT_URI;
	private final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
	private final String url = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
	private final String templateId = "114079";
	public void sendKakaoMessage() {
		String accessToken = this.getAccessToken();
		if(accessToken != null) {
			RestTemplate restTemplate = createRestTemplateWithFormConverter();
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/x-www-form-urlencoded");
			headers.set("Authorization", "Bearer " + accessToken);
			
			// POST 요청 파라미터 (MultiValueMap 사용)
	        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	        String templateObject = "{\n" +
	            "    \"object_type\": \"text\",\n" +
	            "    \"text\": \"텍스트 영역입니다. 최대 200자 표시 가능합니다.\",\n" +
	            "    \"link\": {\n" +
	            "        \"web_url\": \"https://developers.kakao.com\",\n" +
	            "        \"mobile_web_url\": \"https://developers.kakao.com\"\n" +
	            "    },\n" +
	            "    \"button_title\": \"바로 확인\"\n" +
	            "}";

	        params.add("template_object", templateObject);

			
		    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params,headers);
		    ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
		    
		    if (response.getStatusCode() == HttpStatus.OK) {
	            Map<String, Object> body = response.getBody();
	            Integer resultCode = (Integer) body.get("result_code");

	            if (resultCode == 0) {
	                System.out.println("카카오 메시지 전송 성공");
	            } else {
	                System.out.println("카카오 메시지 전송 실패, result_code: " + resultCode);
	            }
	        } else {
	            System.out.println("카카오 메시지 전송 실패, HTTP 상태: " + response.getStatusCode());
	        }
		}
		
	}
	
	public String getAccessToken() {
		  // 가장 최근의 KakaoTokenEntity를 조회
		try {
			KakaoTokenEntity kakaoTokenEntity = kakaoTokenRepository.findFirstByOrderByIdDesc();			
			return kakaoTokenEntity.getAccessToken();
		}catch(Exception e) {
			return null;
		}
	}
	
	public void createToken(String authorizationCode) {
	    // Create a RestTemplate instance
	    RestTemplate restTemplate = new RestTemplate();

	    // Set up the headers for the request
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	    // Create the request parameters
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "authorization_code");
	    params.add("client_id", REST_API_KEY);
	    params.add("redirect_uri", REDIRECT_URI);
	    params.add("code", authorizationCode);

	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

	    ResponseEntity<Map> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, Map.class);

	    if (response.getStatusCode() == HttpStatus.OK) {
	        Map<String, Object> body = response.getBody();
	        System.out.println("Response: " + body);
	        
	        KakaoTokenEntity kakaoTokenEntity = new KakaoTokenEntity(body);
	        kakaoTokenRepository.deleteAll();
	        kakaoTokenRepository.save(kakaoTokenEntity);
	       
	    } else {
	        throw new RuntimeException("Kakao authentication failed: " + response.getStatusCode());
	    }
	}


    @Scheduled(fixedRate = 60000)  // 1분마다 체크
    public void checkAndRefreshAccessToken() {
        // 최신 토큰을 찾아서 만료 여부를 확인
        KakaoTokenEntity kakaoTokenEntity = kakaoTokenRepository.findFirstByOrderByIdDesc();
        
        if (kakaoTokenEntity != null && kakaoTokenEntity.getCreatedAt() != null) {
            // 액세스 토큰 만료 시간을 계산
            LocalDateTime tokenExpiryTime = kakaoTokenEntity.getCreatedAt()
                .plusSeconds(kakaoTokenEntity.getAccessTokenExpires()); // 생성 시간 + 유효 기간

            LocalDateTime now = LocalDateTime.now();

            // 현재 시간이 만료 시간보다 5분 이전이면 갱신
            if (tokenExpiryTime.minusMinutes(5).isBefore(now)) {
                refreshAccessToken(kakaoTokenEntity.getRefreshToken());
            }
        }
    }

    private void refreshAccessToken(String refreshToken) {
        // 리프레시 토큰으로 액세스 토큰 갱신
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);
        params.add("client_id", "YOUR_CLIENT_ID");  // 실제 클라이언트 ID로 변경

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(TOKEN_URL, params, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> body = response.getBody();
            String newAccessToken = (String) body.get("access_token");
            String newRefreshToken = (String) body.get("refresh_token"); // 필요한 경우 리프레시 토큰도 갱신

            // 새로운 액세스 토큰과 리프레시 토큰을 저장
            KakaoTokenEntity newKakaoTokenEntity = new KakaoTokenEntity(body);
            newKakaoTokenEntity.setAccessToken(newAccessToken);
            newKakaoTokenEntity.setRefreshToken(newRefreshToken);

            // 기존 토큰 삭제하고 새로운 토큰 저장
            kakaoTokenRepository.deleteAll(); // 기존 토큰 삭제
            kakaoTokenRepository.save(newKakaoTokenEntity); // 새로운 토큰 저장
        }
    }
    
    public RestTemplate createRestTemplateWithFormConverter() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        
        // application/x-www-form-urlencoded 형식을 처리할 수 있는 FormHttpMessageConverter 추가
        messageConverters.add(new FormHttpMessageConverter());
        
        return restTemplate;
    }
}
