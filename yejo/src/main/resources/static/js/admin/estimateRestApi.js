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
        
        
        fetch(`/api/consultant/file`,{
			method:"POST",
			headers: {
				"Content-Type" : "application/json",
			},
			body : data.floorPlanFile
		})
        .then(response=>{
			return response.blob();
		})
		.then(blob=>{
			const url = URL.createObjectURL(blob);
	        const floorPlanElement = document.getElementById('floorPlan');
	        floorPlanElement.innerHTML = `<a href="${url}" download="${data.floorPlanFile}">다운로드</a>`
		})
		
		fetch(`/api/consultant/file`,{
			method:"POST",
			headers: {
				"Content-Type" : "application/json",
			},
			body : data.referenceFile
		})
        .then(response=>{
			return response.blob();
		})
		.then(blob=>{
			const url = URL.createObjectURL(blob);
	        const floorPlanElement = document.getElementById('referenceFile');
	        floorPlanElement.innerHTML = `<a href="${url}" download="${data.referenceFile}">다운로드</a>`
		})
	})
	.catch(err=>{
		alert('견적 상세보기에 실패했습니다')
		return false;
	})
}