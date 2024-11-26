const saveBannerBtn = document.querySelector("#saveBannerBtn");

const saveBanner = function(formData){
	fetch('/api/banner',{
		method : "post",
		body : formData
	})
	.then(response => {
		if(response.ok){
			alert('저장하였습니다');
			location.reload();
		}else{
			alert('저장에 실패했습니다')
			return false;
		}
	})
	.catch(err => {
		console.log(err);
	})
}

const hideBanner = function(id){
	fetch('/api/banner/'+id,{
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

const deleteBanner = function(id){
	if(confirm("정말 삭제하시겠습니까?")){
		fetch('/api/banner/'+id,{
			method : "DELETE",
		})
		.then(response=>{
			if(response.ok){
				alert('삭제가 완료되었습니다')
				location.reload();
			}else{
				alert('삭제에 실패했습니다')
				return false;
			}
		})
		.catch(err=>{
			alert('삭제에 실패했습니다')
			return false;
		})
	}else{
		return false;
	}
	
}

$(function() {
    $("#sortable").sortable({
      	stop: function(event, ui) {
	    // 전체 ID 배열 가져오기
	    const allIDs = $("#sortable").sortable("toArray");
	    
	    // Fetch API를 사용하여 서버에 새로운 순서 전송
	    fetch('/api/banner/order', {
	        method: "PATCH",
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify(allIDs) // JSON 형태로 전송
	    })
	    .then(response => {
	        if (response.ok) {
	            alert('노출 순서가 수정되었습니다.');
				location.reload();
	        } else {
	            alert('노출 순서 수정에 실패했습니다.');
	            return false;
	        }
	    })
	    .catch(err => {
	        alert(`서버와의 통신에 실패했습니다: ${err.message}`);
	    });
	}


    });
    $("#sortable").disableSelection();
});


saveBannerBtn.addEventListener('click', ()=>{
	console.log("test")
	const bannerTitle = document.querySelector("#bannerTitle").value;
	const bannerImage = document.querySelector("#bannerImage").files[0];
	const formData = new FormData();
	formData.append('bannerTitle', bannerTitle);
	formData.append('bannerImage', bannerImage);
	
	saveBanner(formData);
})

