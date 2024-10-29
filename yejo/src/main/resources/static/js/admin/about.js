document.addEventListener('DOMContentLoaded', function() {
    const MAX_FILE_SIZE = 500 * 1024; // 500KB

    const dropZone = document.getElementById('dropZone');
    const companyImageInput = document.getElementById('companyImage');
    const imagePReview = document.getElementById('imagePReview');
    const cropImage = document.getElementById('cropImage');
    const cropBtn = document.getElementById('cropBtn');
    let cropper = null;  // cropper 초기화

    // 파일 선택 시 이벤트 리스너 추가
    companyImageInput.addEventListener('change', function(event) {
        pReviewImage(event);
    });

    dropZone.addEventListener('click', () => {
        companyImageInput.click();
    });

    dropZone.addEventListener('dragover', (e) => {
        e.preventDefault();
        dropZone.classList.add('dragover');
    });

    dropZone.addEventListener('dragleave', () => {
        dropZone.classList.remove('dragover');
    });

    dropZone.addEventListener('drop', (e) => {
        e.preventDefault();
        dropZone.classList.remove('dragover');
        const files = e.dataTransfer.files;
        if (files.length > 0) {
            companyImageInput.files = files;
            pReviewImage({ target: { files } });
        }
    });

    function pReviewImage(event) {
        const file = event.target.files[0];

        if (file) {
            // 파일 크기 체크
            if (file.size > MAX_FILE_SIZE) {
                alert('이미지 파일 크기는 500KB 이하만 가능합니다.');
                return;
            }

            const reader = new FileReader();
            reader.onload = function (e) {
                cropImage.src = e.target.result;
                cropImage.style.display = 'block';

                if (cropper) {
                    cropper.destroy();  // 기존 cropper 인스턴스 제거
                }

                // 새로운 cropper 인스턴스 생성
                cropper = new Cropper(cropImage, {
                    aspectRatio: 400 / 520,
                    viewMode: 2,
                });

                cropBtn.style.display = 'block';
            };
            reader.readAsDataURL(file);
        }
    }

    cropBtn.addEventListener('click', function () {
        // cropper가 정의되어 있지 않으면 함수 종료
        if (!cropper) {
            alert('이미지를 선택하고 자르기를 해주세요.');
            return;
        }

        const canvas = cropper.getCroppedCanvas({
            width: 400,
            height: 520
        });

        imagePReview.src = canvas.toDataURL('image/jpeg');
        imagePReview.style.display = 'block';
    });

    // 저장
    const saveButton = document.getElementById("save");

    saveButton.addEventListener('click', function(event) {
        event.preventDefault();

        // CKEditor에서 HTML 텍스트 가져오기
        const introductionText = CKEDITOR.instances.companyIntro.getData();

        // 이미지가 없을 때도 저장 가능하게 처리
        if (!cropper) {
            // 텍스트만 저장하는 로직
            const formData = new FormData();
            formData.append('text', introductionText);

            // AJAX 요청으로 서버에 데이터 전송
            fetch('/api/yejostory/save', {
                method: 'POST',
                body: formData
            })
            .then(response => response.text())
            .then(data => {
                alert('저장 성공: ' + data);
                location.reload();
            })
            .catch(error => {
                alert('저장 실패: ' + error.message);
            });

            return; // 이미지가 없으므로 종료
        }

        // 이미지가 있을 경우 처리
        const croppedCanvas = cropper.getCroppedCanvas({
            width: 400,
            height: 520
        });

        // 이미지를 Blob 형태로 변환
        croppedCanvas.toBlob(function(blob) {
            const formData = new FormData();
            formData.append('text', introductionText);
            formData.append('image', blob, 'cropped-image.jpg');

            // AJAX 요청으로 서버에 데이터 전송
            fetch('/api/yejostory/save', {
                method: 'POST',
                body: formData
            })
            .then(response => response.text())
            .then(data => {
                alert('저장 성공: ' + data);
                location.reload();
            })
            .catch(error => {
                alert('저장 실패: ' + error.message);
            });
        }, 'image/png');
    });
});