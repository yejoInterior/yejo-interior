
document.querySelector("#submit_btn").addEventListener('click',()=>{
	const contact1 = document.querySelector("#contact1").value;
    const contact2 = document.querySelector("#contact2").value;
    const contact3 = document.querySelector("#contact3").value;
    const combinedContact = contact1+contact2+contact3;
    
	document.querySelector("#tel").value = combinedContact;
	
	const form = document.querySelector("#form1");
	const formData = new FormData(form);
	const floor_plan_file = document.querySelector("#floor_plan_file").files[0];
	const reference_file = document.querySelector("#reference_file").files[0];
	formData.append("floorPlanFile",floor_plan_file);
	formData.append("referenceFile",reference_file);
	for (const [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
    }
	fetch('/api/consultant',{
		method : "POST",
		body : formData
	})
	.then(response=>{
		if(response.ok){
			alert('견적 요청이 완료되었습니다')
		}else{
			alert('견적 요청에 실패하셨습니다')
		}
	})
})