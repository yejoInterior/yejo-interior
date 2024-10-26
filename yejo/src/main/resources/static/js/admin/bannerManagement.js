document.addEventListener('DOMContentLoaded', function() {
    var modal = document.getElementById('modal');
    var createBannerBtn = document.getElementById('createBannerBtn');
    var closeModalBtn = document.getElementById('closeModalBtn');
    var dropZone = document.getElementById('dropZone');
    var bannerImage = document.getElementById('bannerImage');
    var previewImage = document.getElementById('previewImage');
    var dropZoneText = document.getElementById('dropZoneText'); // 드롭 존 텍스트 요소 추가

    createBannerBtn.onclick = function() {
        modal.style.display = 'block';
    }

    closeModalBtn.onclick = function() {
        modal.style.display = 'none';
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    }

    dropZone.addEventListener('dragover', function(event) {
        event.preventDefault();
        dropZone.style.borderColor = '#007bff';
    });

    dropZone.addEventListener('dragleave', function(event) {
        dropZone.style.borderColor = '#ccc';
    });

    dropZone.addEventListener('drop', function(event) {
        event.preventDefault();
        dropZone.style.borderColor = '#ccc';

        var files = event.dataTransfer.files;
        handleFiles(files);
    });

    dropZone.addEventListener('click', function() {
        bannerImage.click();
    });

    bannerImage.addEventListener('change', function() {
        var files = bannerImage.files;
        handleFiles(files);
    });

    function handleFiles(files) {
        if (files.length > 0) {
            var file = files[0];
            var reader = new FileReader();

            reader.onload = function(event) {
                previewImage.src = event.target.result;
                previewImage.style.display = 'block';
                hideDropZoneText(); // 텍스트 숨기기 함수 호출
            }

            reader.readAsDataURL(file);
            updateInputFile(file); // 파일 인풋 업데이트 함수 호출
        }
    }

    function hideDropZoneText() {
        dropZoneText.style.display = 'none'; // 드롭 존 텍스트 숨기기
    }

    function updateInputFile(file) {
        var dataTransfer = new DataTransfer();
        dataTransfer.items.add(file);
        bannerImage.files = dataTransfer.files; // 인풋 파일 업데이트
    }
});
