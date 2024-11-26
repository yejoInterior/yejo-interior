const savePopup = function(formData){
	
	fetch('/api/popup',{
		method : "POST",
		body : formData
	})
	.then(response=>{
		if(response.ok){
			alert('팝업을 저장하였습니다');
		}
	})
	.catch(err=>{
		console.log(err);
	})
}

const hidePopup = function(){
	fetch('/api/popup',{
		method : "PATCH",
	})
	.then(response=>{
		if(response.ok){
			alert('노출 수정이 완료되었습니다')
			location.reload();
		}else{
			alert("노출 수정에 실패했습니다")
			return false;
		}
	})
	.catch(err=>{
		alert("노출 수정에 실패했습니다")
		return false;
	})
}

document.querySelector("#togglePopupBtn").addEventListener('click',()=>{
	hidePopup();
})


document.querySelector("#createBannerBtn").addEventListener('click',()=>{
	const link = document.querySelector("#popupLink").value;
	const file = document.querySelector("#bannerImageInput").files[0];
	
	const formData = new FormData();
	formData.append("link",link);
	formData.append("file",file);
	
	savePopup(formData);	
})