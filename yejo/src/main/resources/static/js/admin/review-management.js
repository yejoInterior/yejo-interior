document.addEventListener('DOMContentLoaded', function() {
	const hashtagButton = document.getElementById('addHashtagButton');
	const hashtagInput = document.getElementById('ReviewHashtag');
	const hashtagContainer = document.getElementById('hashtagContainer');

	// 해시태그 추가 버튼 클릭 이벤트
	hashtagButton.addEventListener('click', function() {
		const hashtagValue = hashtagInput.value.trim();

		if (hashtagValue) {
			// 해시태그 추가
			const newHashtag = document.createElement('span');
			newHashtag.className = 'badge badge-primary mr-1';
			newHashtag.textContent = "#" + hashtagValue;

			// 해시태그 삭제 버튼 추가
			const removeButton = document.createElement('button');
			removeButton.className = 'btn btn-sm btn-danger ml-1';
			removeButton.textContent = '삭제';
			removeButton.onclick = function() {
				hashtagContainer.removeChild(newHashtag);
				hashtagContainer.removeChild(removeButton);
			};

			hashtagContainer.appendChild(newHashtag);
			hashtagContainer.appendChild(removeButton);

			// 입력 필드 초기화
			hashtagInput.value = '';
		}
	});

	document.getElementById('ReviewImage').addEventListener('change', function() {
		const imageFile = this.files[0];

		// 파일 타입 확인
		if (imageFile && !imageFile.type.startsWith('image/')) {
			alert('이미지 파일만 업로드 가능합니다.'); // 오류 메시지
			this.value = ''; // 선택된 파일 초기화
			return;
		}
		
		// 파일 용량 확인 (1MB = 1024 * 1024 bytes)
        if (imageFile.size > 1 * 1024 * 1024) {
            alert('파일 크기는 1MB 이하만 업로드 가능합니다.'); // 오류 메시지
            this.value = ''; // 선택된 파일 초기화
            return; // 함수 종료
        }
	});

	// 리뷰 폼 제출 이벤트
	const ReviewForm = document.getElementById('ReviewForm');
	ReviewForm.addEventListener('submit', async function(e) {
		e.preventDefault(); // 기본 폼 제출 방지

		const formData = new FormData();
		formData.append('title', document.getElementById('ReviewTitle').value);
		formData.append('url', document.getElementById('ReviewUrl').value);
		formData.append('content', document.getElementById('ReviewContent').value);
		console.log(formData.get("url"));

		// 해시태그 추가
		const hashtags = hashtagContainer.querySelectorAll('.badge');
		hashtags.forEach(function(badge) {
			formData.append('tags', badge.textContent);
		});

		const imageFile = document.getElementById('ReviewImage').files[0];
		if (imageFile) {
			formData.append('image', imageFile); // 이미지 파일 추가
		}

		// Fetch API를 사용하여 AJAX 요청
		try {
			const response = await fetch('/api/Reviews', {
				method: 'POST',
				body: formData,
			});

			if (response.ok) {
				alert('리뷰가 성공적으로 저장되었습니다!'); // 성공 메시지
				ReviewForm.reset(); // 폼 초기화
				hashtagContainer.innerHTML = ''; // 해시태그 컨테이너 초기화
			} else {
				const errorText = await response.text();
				alert('오류 발생: ' + errorText); // 오류 메시지
			}
		} catch (error) {
			alert('오류 발생: ' + error.message); // 네트워크 오류 메시지
		}
	});
});