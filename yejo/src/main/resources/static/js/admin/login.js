function checkPassword() {
    var password = document.getElementById('passwordInput').value;
    if (password) {  // 비밀번호가 있을 때만 전송
        // 로그인 성공 시 서버로 세션 값을 설정 요청
        fetch('/admin/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'  // 요청의 Content-Type을 JSON으로 설정
            },
            body: JSON.stringify({ password: password })  // 비밀번호를 JSON 형식으로 전송
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                window.location.href = './banner'; // 로그인 성공 시 'banner' 페이지로 이동
            } else {
                document.getElementById('errorMessage').textContent = '패스워드가 올바르지 않습니다.';
            }
        })
        .catch(error => console.error('Error:', error));
    } else {
        document.getElementById('errorMessage').textContent = '패스워드를 입력해주세요.';
    }
}

// Enter 키 이벤트 처리
document.addEventListener('DOMContentLoaded', () => {
	// 요소 선택
	const modalBackground = document.getElementById('modalBackground');
	const confirmChangeButton = document.getElementById('confirmChange');

	// 전역 함수로 선언
	window.changePwd = function () {
	  modalBackground.style.display = 'block';
	};

	// 모달 닫기 (변경 버튼 클릭 시)
	confirmChangeButton.addEventListener('click', () => {
	  const newPassword = document.getElementById('newPassword').value;

	  if (newPassword.trim() === '') {
	    alert('패스워드를 입력해주세요.');
	  } else {
	    // 비밀번호를 서버로 보내기
	    fetch('/admin/changePassword', {
	      method: 'POST',
	      headers: {
	        'Content-Type': 'application/json',
	      },
	      body: JSON.stringify({ password: newPassword }) // 비밀번호를 서버로 보냄
	    })
	    .then(response => response.text())  // 서버의 응답을 JSON으로 처리
	    .then(data => {
	      if (data === "비밀번호가 성공적으로 변경되었습니다.") {
	        alert('패스워드가 성공적으로 변경되었습니다.');
	      } else {
	        alert('패스워드 변경에 실패했습니다.');
	      }
	    })
	    .catch(error => {
	      console.error('Error:', error);
	      alert('서버와의 통신 중 문제가 발생했습니다.');
	    });

	    modalBackground.style.display = 'none'; // 모달 닫기
	  }
	});

	// 배경 클릭 시 모달 닫기
	modalBackground.addEventListener('click', (event) => {
	  if (event.target === modalBackground) {
	    modalBackground.style.display = 'none';
	  }
	});
	
    var passwordInput = document.getElementById('passwordInput');
    passwordInput.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            checkPassword();  // Enter 키로 비밀번호 확인
        }
    });
});


