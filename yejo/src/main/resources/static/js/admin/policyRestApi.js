const savePolicy = function(){
	const policyContent = document.querySelector("#policy-content").value;
	const guideContent = document.querySelector("#autoResizeTextarea").value;
	const data = {
		"policyContent" : policyContent,
		"guideContent" : guideContent
	}
	console.log(data)
	fetch('/api/policy',{
		method : "PATCH",
		headers : {
			'Content-Type': 'application/json'
		},
		body : JSON.stringify(data)
	})
	.then(response=>{
		if(response.ok){
			alert('수정이 완료되었습니다.');
		}
	})
	.catch(err=>{
		alert('서버 오류로 수정에 실패했습니다. 관리자에게 문의해주세요')
	})
	
}

document.querySelector("#createPolicyBtn").addEventListener("click", savePolicy);
