 

  // 쿠키 설정 함수
    function setCookie(name, value, days) {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    }

    // 쿠키 가져오기 함수
    function getCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') c = c.substring(1, c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
        }
        return null;
    }

    // 팝업 닫기 함수
    function popupClose() {
        document.getElementById('popup2').style.display = 'none';
        document.getElementById('overlay').style.display = 'none';
    }

    // 오늘 하루 열지 않기 체크박스 클릭 시 쿠키 설정
    function setNoPopup() {
        var checkBox = document.getElementById("popup_check2");
        if (checkBox.checked) {
            setCookie("noPopup", "true", 1); // 1일 동안 쿠키 유지
        } else {
            setCookie("noPopup", "false", 1);
        }
    }

    // 페이지 로드 시 팝업 표시 여부 결정
    window.onload = function() {
        if (getCookie("noPopup") !== "true") {
            document.getElementById('popup2').style.display = 'block';
            document.getElementById('overlay').style.display = 'block';
        }else{
			document.getElementById('popup2').style.display = 'none';
            document.getElementById('overlay').style.display = 'none';
		}
    };