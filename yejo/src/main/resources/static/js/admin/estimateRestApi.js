function showEstimateAll(id){
	fetch(`/api/consultant/${id}`)
	.then(response=>{
		if(response.ok){
			return response.json();		
		}else{
			alert('견적 상세보기에 실패했습니다')
			return false;
		}
	})
	.then(data=>{
		console.log(data)
		document.getElementById('customerName').textContent = data.name;
        document.getElementById('customerPhone').textContent = data.tel;
        document.getElementById('constructionAddress').value = `(${data.post}) ${data.address1}, ${data.address2}`;
        document.getElementById('spaceSize').value = data.areaSize;
        document.getElementById('roomCount').value = data.roomCount;
        document.getElementById('bathroomCount').value = data.bathCount;
        document.getElementById('constructionStartDate').value = data.constructionStartDate;
        document.getElementById('moveInDate').value = data.moveInDate;
        document.getElementById('spaceType').value = data.spaceType;
        document.getElementById('budget').value = data.budget;
        document.getElementById('referenceUrl').value = data.referenceUrl;
        document.getElementById('questions').value = data.additionalInquiries;
        console.log(data.floorPlanFiles);
        console.log(data.referenceFiles);
        
        const floorPlanElement = document.getElementById('floorPlan');
		floorPlanElement.innerHTML = ''; // 이전 내용 제거
		
		data.floorPlanFiles.forEach(file => {
		    const url = file.storagePath; // 파일의 저장 경로
		    const link = document.createElement('a'); // 새로운 a 태그 생성
		    link.href = url; // 다운로드 링크 설정
		    link.download = ''; // 다운로드할 파일의 이름 설정 (비워두면 URL의 마지막 부분을 사용)
		    link.innerText = `${file.realName} 다운로드`; // 링크 텍스트 설정
		    link.addEventListener('click', function(e) {
		        e.preventDefault(); // 기본 동작 방지
		        fetch('/api/consultant/file',{
					method:"POST",
					body : url
				})
		            .then(response => {
		                if (!response.ok) {
		                    throw new Error('Network response was not ok');
		                }
		                return response.blob();
		            })
		            .then(blob => {
		                const blobUrl = URL.createObjectURL(blob);
		                const downloadLink = document.createElement('a');
		                downloadLink.href = blobUrl;
		                downloadLink.download = file.realName; // 파일 이름 설정
		                document.body.appendChild(downloadLink);
		                downloadLink.click();
		                document.body.removeChild(downloadLink); // 다운로드 후 링크 제거
		            })
		            .catch(error => console.error('Download error:', error));
		    });
		    
		    floorPlanElement.appendChild(link); // 링크를 DOM에 추가
		    floorPlanElement.appendChild(document.createElement('br')); // 줄바꿈 추가
		});

	
		// referenceFile 처리
		const referenceElement = document.getElementById('referenceFile');
		referenceElement.innerHTML = ''; // 이전 내용 제거
		
		data.referenceFiles.forEach(file => {
		    const url = file.storagePath; // 파일의 저장 경로
		    const link = document.createElement('a'); // 새로운 a 태그 생성
		    link.href = url; // 다운로드 링크 설정
		    link.download = ''; // 다운로드할 파일의 이름 설정
		    link.innerText = `${file.realName} 다운로드`; // 링크 텍스트 설정
		
		    link.addEventListener('click', function(e) {
		        e.preventDefault(); // 기본 동작 방지
		        fetch('/api/consultant/file',{
					method:"POST",
					body : url
				})
		        .then(response => {
		            if (!response.ok) {
		                throw new Error('Network response was not ok');
		            }
		            return response.blob();
		        })
		        .then(blob => {
		            const blobUrl = URL.createObjectURL(blob);
		            const downloadLink = document.createElement('a');
		            downloadLink.href = blobUrl;
		            downloadLink.download = file.realName; // 파일 이름 설정
		            document.body.appendChild(downloadLink);
		            downloadLink.click();
		            document.body.removeChild(downloadLink); // 다운로드 후 링크 제거
		        })
		        .catch(error => console.error('Download error:', error));
		    });
		
		    referenceElement.appendChild(link); // 링크를 DOM에 추가
		    referenceElement.appendChild(document.createElement('br')); // 줄바꿈 추가
		});

	})
	.catch(err=>{
		alert('견적 상세보기에 실패했습니다')
		return false;
	})
}