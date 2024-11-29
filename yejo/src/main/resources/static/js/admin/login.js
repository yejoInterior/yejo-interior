function checkPassword() {
    var password = document.getElementById('passwordInput').value;
    console.log(password);
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