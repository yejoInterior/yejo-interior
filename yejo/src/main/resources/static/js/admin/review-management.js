document.getElementById('addHashtagButton').addEventListener('click', function() {
	const hashtagInput = document.getElementById('reviewHashtag');
	const hashtagValue = hashtagInput.value.trim();

	if (hashtagValue) {
		// 해시태그 추가
		const hashtagContainer = document.getElementById('hashtagContainer');
		const newHashtag = document.createElement('span');
		newHashtag.className = 'badge badge-primary mr-1';
		newHashtag.textContent = hashtagValue;

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
