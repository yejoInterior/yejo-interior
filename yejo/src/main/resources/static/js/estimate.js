// 모달 열기
document.querySelectorAll('.openQuoteDetailModal').forEach(function (button) {
    button.addEventListener('click', function (event) {
        event.preventDefault(); // 링크 클릭 기본 동작 방지

        const row = this.closest('tr');

        // 이전에 선택된 행의 'selected' 클래스 제거
        document.querySelectorAll('.table tbody tr').forEach(r => r.classList.remove('selected'));
        
        // 현재 행에 'selected' 클래스 추가
        row.classList.add('selected');

        document.getElementById('customerName').textContent = row.cells[3].textContent;
        document.getElementById('customerPhone').textContent = row.cells[4].textContent;

        // 모달 열기
        const modal = document.getElementById('quoteDetailModal');
        modal.style.display = 'block'; // 모달을 표시
        setTimeout(() => {
            modal.classList.add('show'); // 모달 슬라이드 인 효과 추가
        }, 10); // 애니메이션을 적용하기 위한 지연

        // 테이블 크기 조정
        const tableResponsive = document.querySelector('.table-responsive');
        tableResponsive.classList.add('shrink'); // 테이블에 shrink 클래스 추가
    });
});

// 모달 닫기 함수
function closeModal() {
    const modal = document.getElementById('quoteDetailModal');
    modal.classList.remove('show'); // show 클래스 제거

    // 테이블 크기 원래대로 복원
    const tableResponsive = document.querySelector('.table-responsive');
    tableResponsive.classList.remove('shrink'); // 테이블의 shrink 클래스 제거

    // 애니메이션 시간 후 모달 숨김
    setTimeout(() => {
        modal.style.display = 'none'; // 모달을 숨김
    }, 300); // 애니메이션 시간과 일치하도록
}

// 모달 닫기 버튼 클릭 이벤트
document.getElementById('closeQuoteDetailModal').onclick = closeModal;

// 모달 외부 클릭 시 닫기
window.onclick = function (event) {
    const modal = document.getElementById('quoteDetailModal');

    // 모달 배경 부분을 클릭했을 때만 닫기
    if (event.target === modal) {
        closeModal(); // 모달 닫기
    }
};
