function handleThumbnailUpload(event) {
    const file = event.target.files[0];
    if (!file) return; // 파일이 없으면 함수 종료

    const MAX_FILE_SIZE = 1000 * 1024; // 1MB 제한
    if (file.size > MAX_FILE_SIZE) {
        alert("파일 크기는 1MB 이하로만 업로드 가능합니다.");
        return;
    }

    const reader = new FileReader();
    reader.onload = function (e) {
        const preview = document.getElementById('imagePReview');
        preview.src = e.target.result;
        preview.style.display = 'block';
    };
    reader.readAsDataURL(file);
}

function handlePortfolioUpload(event) {
    const files = event.target.files;
    if (!files || files.length === 0) return; // 파일이 없으면 함수 종료

    document.getElementById('portfolioImagePReviewWrapper').innerHTML = '';  // 미리보기 초기화

    Array.from(files).forEach(file => {
        const MAX_FILE_SIZE = 1000 * 1024;
        if (file.size > MAX_FILE_SIZE) {
            alert("포트폴리오 사진의 파일 크기는 1MB 이하로만 업로드 가능합니다.");
            return;
        }

        const reader = new FileReader();
        reader.onload = function (e) {
            const img = document.createElement('img');
            img.src = e.target.result;
            img.classList.add('img-thumbnail');
            document.getElementById('portfolioImagePReviewWrapper').appendChild(img);
        };
        reader.readAsDataURL(file);
    });
}

// 저장 버튼 클릭 시 데이터 전송
document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault(); // 기본 폼 전송 막기

    const formData = new FormData(event.target);

    fetch('/api/portfolio/save', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())  // JSON으로 파싱
    .then(data => {
        const alertContainer = document.getElementById('alertContainer');
        alertContainer.innerHTML = '';  // 이전 메시지 제거

        const alertMessage = document.createElement('div');
        alertMessage.className = 'alert';
        
        // 성공/실패 메시지 설정
        if (data.success) {
            alertMessage.classList.add('alert-success');
            alertMessage.textContent = data.message;
        } else {
            alertMessage.classList.add('alert-danger');
            alertMessage.textContent = '오류가 발생했습니다: ' + data.message;
        }

        alertContainer.appendChild(alertMessage);

        // 3초 후 메시지 자동 제거
        setTimeout(() => {
            alertMessage.remove();
        }, 3000);
    })
    .catch(error => {
        console.error('Error:', error);
        alert("저장 중 오류가 발생했습니다.");
    });
});

document.getElementById('dropZone').addEventListener('click', function () {
    document.getElementById('companyImage').click();
});

document.getElementById('portfolioDropZone').addEventListener('click', function () {
    document.getElementById('portfolioImages').click();
});

// 드래그 앤 드롭을 위한 이벤트 리스너 추가
const dropZone = document.getElementById('dropZone');
dropZone.addEventListener('dragover', function (event) {
    event.preventDefault();
    dropZone.classList.add('dragover');
});
dropZone.addEventListener('dragleave', function () {
    dropZone.classList.remove('dragover');
});
dropZone.addEventListener('drop', function (event) {
    event.preventDefault();
    dropZone.classList.remove('dragover');
    document.getElementById('companyImage').files = event.dataTransfer.files;
    handleThumbnailUpload({ target: { files: event.dataTransfer.files } });
});

const portfolioDropZone = document.getElementById('portfolioDropZone');
portfolioDropZone.addEventListener('dragover', function (event) {
    event.preventDefault();
    portfolioDropZone.classList.add('dragover');
});
portfolioDropZone.addEventListener('dragleave', function () {
    portfolioDropZone.classList.remove('dragover');
});
portfolioDropZone.addEventListener('drop', function (event) {
    event.preventDefault();
    portfolioDropZone.classList.remove('dragover');
    document.getElementById('portfolioImages').files = event.dataTransfer.files;
    handlePortfolioUpload({ target: { files: event.dataTransfer.files } });
});

function deletePortfolio(id) {
    if (confirm("정말로 삭제하시겠습니까?")) {
        fetch(`/api/portfolio/delete/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                alert("삭제가 완료되었습니다.");
                location.reload();  // 페이지 새로고침하여 변경 사항 반영
            } else {
                alert("삭제에 실패했습니다.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("삭제 중 오류가 발생했습니다.");
        });
    }
}
