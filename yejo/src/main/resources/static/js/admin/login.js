function checkPassword() {
    const password = document.getElementById('passwordInput').value;
    const changePwdButton = document.getElementById('changePwdButton'); // 버튼 선택

    if (password) {  // 비밀번호가 있을 때만 전송
        fetch('/admin/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ password: password }) // 비밀번호 전송
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                document.getElementById('errorMessage').textContent = ''; // 오류 메시지 제거
                changePwdButton.disabled = false; // 패스워드 변경 버튼 활성화
                changePwdButton.style.backgroundColor = '#4CAF50'; // 활성화 색상
                changePwdButton.style.cursor = 'pointer'; // 활성화 시 커서 변경
                window.location.href = './estimate'; // 로그인 성공 시 이동
            } else {
                document.getElementById('errorMessage').textContent = '패스워드가 올바르지 않습니다.';
                changePwdButton.disabled = true; // 버튼 비활성화 유지
                changePwdButton.style.backgroundColor = '#ccc'; // 비활성화 색상
                changePwdButton.style.cursor = 'not-allowed'; // 비활성화 커서
            }
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('errorMessage').textContent = '서버와의 통신 중 문제가 발생했습니다.';
            changePwdButton.disabled = true; // 버튼 비활성화 유지
            changePwdButton.style.backgroundColor = '#ccc'; // 비활성화 색상
            changePwdButton.style.cursor = 'not-allowed'; // 비활성화 커서
        });
    } else {
        document.getElementById('errorMessage').textContent = '패스워드를 입력해주세요.';
        changePwdButton.disabled = true; // 버튼 비활성화 유지
        changePwdButton.style.backgroundColor = '#ccc'; // 비활성화 색상
        changePwdButton.style.cursor = 'not-allowed'; // 비활성화 커서
    }
}

// Enter 키 및 input 이벤트 처리
document.addEventListener('DOMContentLoaded', () => {
    const modalBackground = document.getElementById('modalBackground');
    const confirmChangeButton = document.getElementById('confirmChange');
    const passwordInput = document.getElementById('passwordInput');
    const changePwdButton = document.getElementById('changePwdButton');

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
            fetch('/admin/changePassword', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ password: newPassword })
            })
            .then(response => response.text())
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

            modalBackground.style.display = 'none';
        }
    });

    // 배경 클릭 시 모달 닫기
    modalBackground.addEventListener('click', (event) => {
        if (event.target === modalBackground) {
            modalBackground.style.display = 'none';
        }
    });

    // Enter 키로 로그인
    passwordInput.addEventListener('keydown', function (event) {
        if (event.key === 'Enter') {
            checkPassword();
        }
    });

    // input 이벤트로 버튼 상태 실시간 업데이트
    passwordInput.addEventListener('input', function () {
        const password = passwordInput.value.trim();
        if (password) {
            fetch('/admin/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ password: password }) // 비밀번호 전송
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    changePwdButton.disabled = false;
                    changePwdButton.style.backgroundColor = '#4CAF50';
                    changePwdButton.style.cursor = 'pointer';
                } else {
                    changePwdButton.disabled = true;
                    changePwdButton.style.backgroundColor = '#ccc';
                    changePwdButton.style.cursor = 'not-allowed';
                }
            });
        } else {
            changePwdButton.disabled = true;
            changePwdButton.style.backgroundColor = '#ccc';
            changePwdButton.style.cursor = 'not-allowed';
        }
    });
});
