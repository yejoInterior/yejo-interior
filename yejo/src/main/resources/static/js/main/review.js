let currentPage = 1; // 현재 페이지 번호
const reviewsContainer = document.getElementById('reviews-container');
const loadMoreButton = document.getElementById('load-more');

// 리뷰 데이터를 가져오는 함수
async function fetchReviews(page) {
    try {
        const response = await fetch(`/api/reviews?page=${page}`);
        if (!response.ok) {
            throw new Error('네트워크 응답이 정상이 아닙니다.');
        }
        const reviews = await response.json();
        return reviews;
    } catch (error) {
        console.error('리뷰를 가져오는 중 오류 발생:', error);
        return []; // 오류 발생 시 빈 배열 반환
    }
}

// 리뷰를 화면에 표시하는 함수
function displayReview(review) {
    const reviewElement = document.createElement('div');
    reviewElement.classList.add('Review'); // 후기 div 클래스 추가
    reviewElement.innerHTML = `
        <a href="${review.url}" class="link-wrapper" target="_blank">
            <div class="content">
                <span class="title">${review.title}</span>
                <p>${review.content}</p>
                <div class="hashtags">
                    ${Array.isArray(review.tagList) && review.tagList.length > 0
                        ? review.tagList.map(tag => `<span class="hashtag">${tag}</span>`).join('')
                        : '<span class="hashtag">#해시태그 없음</span>'}
                </div>
            </div>
            <img src="${review.imagePath}" class="image" alt="대표 이미지">
        </a>
    `;
    reviewsContainer.appendChild(reviewElement); // 새로운 후기를 컨테이너에 추가
}

// "후기 더보기" 버튼 클릭 시 처리
loadMoreButton.addEventListener('click', async (event) => {
    event.preventDefault(); // 링크 기본 동작 방지
    currentPage++; // 페이지 번호 증가
    const newReviews = await fetchReviews(currentPage); // 새로운 리뷰 가져오기
    if (newReviews && newReviews.length > 0) {
        newReviews.forEach(displayReview); // 가져온 리뷰 표시
    } else {
        // 추가된 리뷰가 없을 경우 더보기 버튼 숨기기
        loadMoreButton.style.display = 'none'; // 더 이상 리뷰가 없으면 버튼 숨기기
    }
});

// 초기 리뷰 로드
(async () => {
    const initialReviews = await fetchReviews(currentPage);
    if (initialReviews && initialReviews.length > 0) {
        initialReviews.forEach(displayReview); // 초기 리뷰 표시
    } else {
        const noReviewsMessage = document.createElement('div');
        noReviewsMessage.classList.add('no-reviews');
        noReviewsMessage.textContent = '작성된 후기가 없습니다.';
        reviewsContainer.appendChild(noReviewsMessage);
    }
})();
