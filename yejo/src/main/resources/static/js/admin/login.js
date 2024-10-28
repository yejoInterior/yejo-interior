function checkPassword() {
    var password = document.getElementById('passwordInput').value;
    if (password === 'yejo7048@') {
        window.location.href = './banner'; // './banner'로 이동
    } else {
        document.getElementById('errorMessage').textContent = '패스워드가 올바르지 않습니다.';
    }
}