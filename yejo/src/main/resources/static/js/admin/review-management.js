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

	// 후기 작성 폼 제출 이벤트
	const ReviewForm = document.getElementById('ReviewForm');
	ReviewForm.addEventListener('submit', async function(e) {
		e.preventDefault(); // 기본 폼 제출 방지

		const formData = new FormData();
		formData.append('title', document.getElementById('ReviewTitle').value);
		formData.append('url', document.getElementById('ReviewUrl').value);
		formData.append('content', document.getElementById('ReviewContent').value);

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
			const response = await fetch('/api/reviews', {
				method: 'POST',
				body: formData,
			});

			const result = await response.text(); // 서버에서 반환된 메시지를 읽음

			if (response.ok) {
				alert(result); // 서버에서 반환된 성공 메시지 출력
				ReviewForm.reset(); // 폼 초기화
				hashtagContainer.innerHTML = ''; // 해시태그 컨테이너 초기화
			} else {
				alert('오류 발생: ' + result); // 서버에서 반환된 오류 메시지 출력
			}
		} catch (error) {
			alert('오류 발생: ' + error.message); // 네트워크 오류 메시지
		}
	});

	// 후기 수정 폼 제출 이벤트
	const EditReviewForm = document.getElementById('EditReviewForm');
	EditReviewForm.addEventListener('submit', async function(e) {
		e.preventDefault(); // 기본 폼 제출 방지

		const formData = new FormData();
		formData.append('idx', document.getElementById("editReviewId").value);
		formData.append('title', document.getElementById("EditReviewTitle").value);
		formData.append('url', document.getElementById("EditReviewUrl").value);
		formData.append('content', document.getElementById("EditReviewContent").value);

		// 해시태그 추가
		const hashtags = document.getElementById('editHashtagContainer').querySelectorAll('.badge'); // 수정된 부분
		hashtags.forEach(function(badge) {
			formData.append('tags', badge.textContent);
		});

		const imageFile = document.getElementById('EditReviewImage').files[0];
		if (imageFile) {
			formData.append('image', imageFile); // 새 이미지 파일 추가
		}

		// 로딩 인디케이터 표시
		const loadingIndicator = document.createElement('div');
		loadingIndicator.innerText = '수정 중...';
		document.body.appendChild(loadingIndicator);

		try {
			const response = await fetch(`/api/reviews/${document.getElementById("editReviewId").value}`, {
				method: 'PUT',
				body: formData // FormData 사용
			});

			if (response.ok) {
				alert('리뷰가 성공적으로 수정되었습니다!'); // 성공 메시지
				$('#editReviewModal').modal('hide'); // 수정 모달 닫기
				location.reload(); // 페이지 새로고침
			} else {
				const errorText = await response.text();
				alert('오류 발생: ' + errorText); // 서버 오류 메시지
			}
		} catch (error) {
			alert('오류 발생: ' + error.message); // 네트워크 오류 메시지
		} finally {
			// 로딩 인디케이터 제거
			document.body.removeChild(loadingIndicator);
		}
	});

});

// 리뷰 삭제 함수
async function deleteReview(reviewId) {
	if (confirm("정말 리뷰를 삭제하시겠습니까?")) {
		const loadingIndicator = document.createElement('div');
		loadingIndicator.innerText = '삭제 중...';
		document.body.appendChild(loadingIndicator);

		try {
			const response = await fetch(`/api/reviews/${reviewId}`, {
				method: 'DELETE', // 삭제 요청은 DELETE 메소드 사용
			});

			if (response.ok) {
				alert('리뷰가 성공적으로 삭제되었습니다!'); // 성공 메시지
				location.reload(); // 페이지 새로고침
			} else {
				const errorText = await response.text();
				alert('오류 발생: ' + errorText); // 서버 오류 메시지
			}
		} catch (error) {
			alert('오류 발생: ' + error.message); // 네트워크 오류 메시지
		} finally {
			// 로딩 인디케이터 제거
			document.body.removeChild(loadingIndicator);
		}
	}
}

// 후기 수정 모달 열기 함수
async function openEditReviewModal(reviewId) {
	const loadingIndicator = document.createElement('div');
	loadingIndicator.innerText = '데이터를 가져오는 중...';
	document.body.appendChild(loadingIndicator);

	try {
		const response = await fetch(`/api/reviews/${reviewId}`);
		if (!response.ok) {
			throw new Error('서버 오류: ' + response.statusText);
		}

		const review = await response.json();

		// 모달 창의 필드에 데이터 채우기
		document.getElementById("EditReviewTitle").value = review.title;
		document.getElementById("EditReviewUrl").value = review.url;
		document.getElementById("EditReviewContent").value = review.content;
		document.getElementById("editReviewId").value = review.idx; // 수정할 리뷰 ID 저장

		// 기존 해시태그 로드
		const editHashtagContainer = document.getElementById("editHashtagContainer");
		editHashtagContainer.innerHTML = ''; // 기존 해시태그 초기화
		review.tagList.forEach(hashtag => {
			// 해시태그 요소 생성
			const newHashtag = document.createElement('span');
			newHashtag.className = 'badge badge-primary mr-1';
			newHashtag.textContent = hashtag;

			// 해시태그 삭제 버튼 추가
			const removeButton = document.createElement('button');
			removeButton.className = 'btn btn-sm btn-danger ml-1';
			removeButton.textContent = '삭제';
			removeButton.onclick = function() {
				editHashtagContainer.removeChild(newHashtag);
				editHashtagContainer.removeChild(removeButton);
			};

			editHashtagContainer.appendChild(newHashtag);
			editHashtagContainer.appendChild(removeButton);
		});

		// 모달 열기
		$('#editReviewModal').modal('show');
	} catch (error) {
		alert('오류 발생: ' + error.message); // 에러 메시지 표시
	} finally {
		// 로딩 인디케이터 제거
		document.body.removeChild(loadingIndicator);
	}
}

// 해시태그 추가 버튼 클릭 이벤트
const addEditHashtagButton = document.getElementById("addEditHashtagButton");
const editHashtagContainer = document.getElementById("editHashtagContainer");
const editHashtagInput = document.getElementById("EditReviewHashtag");

addEditHashtagButton.addEventListener('click', function() {
	const hashtagValue = editHashtagInput.value.trim();

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
			editHashtagContainer.removeChild(newHashtag);
			editHashtagContainer.removeChild(removeButton);
		};

		editHashtagContainer.appendChild(newHashtag);
		editHashtagContainer.appendChild(removeButton);

		// 입력 필드 초기화
		editHashtagInput.value = '';
	}
});



// 파일 입력 변경 이벤트 추가
document.getElementById("EditReviewImage").addEventListener("change", function(event) {
	const file = event.target.files[0];
	const imagePreview = document.getElementById("imagePreview");

	if (file) {
		const reader = new FileReader();
		reader.onload = function(e) {
			imagePreview.src = e.target.result; // 파일 미리보기 설정
			imagePreview.style.display = 'block'; // 미리보기 표시
		}
		reader.readAsDataURL(file);
	} else {
		imagePreview.src = ""; // 파일이 없을 경우 미리보기 초기화
		imagePreview.style.display = 'none'; // 미리보기 숨김
	}
});

