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
        document.getElementById('deleteBtn').value = data.id;
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
		    link.innerText = `${file.realName}`; // 링크 텍스트 설정
		
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
		console.log(err)
		alert('견적 상세보기에 실패했습니다')
		return false;
	})
}

const deleteBtn = document.querySelector("#deleteBtn");
if(deleteBtn != null){
	deleteBtn.addEventListener('click',function(){
		if(confirm('견적 및 평면도를 포함한 모든 파일이 삭제됩니다. 정말 삭제하시겠습니까?')){
			fetch(`/api/consultant/${this.value}`,{
				method: "delete"
			})
			.then(response=>{
				if(response.ok){
					
			    alert('견적 삭제에 성공하였습니다.')
				location.reload();
				}else{
					alert('서버 오류로 견적 삭제에 실패했습니다.')
					return false;
				}
			})
			.catch(err=>{
				alert('서버 오류로 견적 삭제에 실패했습니다.')
				return false;
			})
		}
		
	})			
}

document.querySelector("#statusBtn").addEventListener('click',function(){
	fetch(`/api/consultant/${this.value}`,{
		method: "PATCH"
	})
	.then(response=>{
		if(response.ok){
			alert("상태를 변경하였습니다.")
			location.reload();
		}else{
			alert("서버 오류로 상태 변경에 실패했습니다.");
			return false;
		}
	})
	.catch(err=>{
		alert('서버 오류로 상태 변경에 실패했습니다.')
		return false;
	})
})


document.addEventListener('DOMContentLoaded', () => {
    // 유입 경로 데이터 가져오기
    fetch('/admin/inflow-stats')
        .then(response => response.json())
        .then(data => {
            const labels = Object.keys(data);
            const values = Object.values(data);

            // 차트 생성
            const ctx = document.getElementById('inflowChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: '유입 경로 통계',
                        data: values,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });

            // 테이블 데이터 추가
            const tableBody = document.getElementById('inflowTable').querySelector('tbody');
            labels.forEach((label, index) => {
                const row = document.createElement('tr');
                const inflowCell = document.createElement('td');
                const countCell = document.createElement('td');

                inflowCell.textContent = label;
                countCell.textContent = values[index];

                row.appendChild(inflowCell);
                row.appendChild(countCell);
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error fetching inflow stats:', error));
});
