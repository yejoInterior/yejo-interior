const agree = document.querySelector("#p_chk");


document.querySelector("#submit_btn").addEventListener('click', () => {
	if(agree.checked){
		const contact1 = document.querySelector("#contact1").value;
	    const contact2 = document.querySelector("#contact2").value;
	    const contact3 = document.querySelector("#contact3").value;
	    const combinedContact = contact1 + contact2 + contact3;
	
	    document.querySelector("#tel").value = combinedContact;
	
	    const form = document.querySelector("#form1");
	    const formData = new FormData(form);
	
	    // 모든 파일을 가져와서 formData에 추가
	    const floorPlanFiles = document.querySelector("#floor_plan_file").files;
	    const referenceFiles = document.querySelector("#reference_file").files;
	
	    // floorPlanFile 추가
	    for (const file of floorPlanFiles) {
	        formData.append("floorPlanFile", file);
	    }
	
	    // referenceFile 추가
	    for (const file of referenceFiles) {
	        formData.append("referenceFile", file);
	    }
	
	    // 확인을 위해 formData에 추가된 항목 출력
	    for (const [key, value] of formData.entries()) {
	        if(key != 'referenceUrl' && key != 'additionalInquiries' && value==""){
				alert('필수 입력 값을 입력해주세요');
				document.getElementsByName(key)[0].focus();
				return false;
			}
	    }
	
	    // 서버에 데이터 전송
	    fetch('/api/consultant', {
	        method: "POST",
	        body: formData
	    })
	    .then(response => {
	        if (response.ok) {
	            alert('견적 요청이 완료되었습니다');
	            //location.href = "/";
	        } else {
	            alert('견적 요청에 실패하셨습니다');
	        }
	    })
	    .catch(error => {
	        console.error('Error:', error);
	        alert('서버 오류가 발생했습니다.');
	    });
	}else{
		alert('개인정보 수집 및 이용에 동의하셔야합니다');
		agree.focus();
		return false;
	}
	
   
});
