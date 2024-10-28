function budgeInput(){
	if (document.getElementById('budget_input').checked) {
        document.getElementById('budget_money').readOnly = false;
    } else {
        document.getElementById('budget_money').readOnly = true;
        document.getElementById('budget_money').value='';
    }
}