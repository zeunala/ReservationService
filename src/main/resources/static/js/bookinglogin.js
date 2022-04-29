var EmailCheckObj = {
	emailValid: false,
	checkEmailValid: function(value) {
		if ((/^[\w+_]\w+@\w+\.(\w+\.)?\w+$/).test(value)) { // ex. sample@naver.com, sample@yahoo.co.kr
			this.emailValid = true;
			CommonClassEditObj.addClass(document.querySelector(".alert_message"), "hide");
		} else {
			this.emailValid = false;
			CommonClassEditObj.removeClass(document.querySelector(".alert_message"), "hide");
		}
	}
}

var EventObj = {
	setEventListeners: function() {
		document.querySelector(".login_form > input").addEventListener("change", (evt) => {
			EmailCheckObj.checkEmailValid(evt.target.value);
		});
		document.querySelector("#form1").addEventListener("submit", (evt) => {
			if (!EmailCheckObj.emailValid) {
				evt.preventDefault();
			}
		});
	}
}

function initConfig() {
	EventObj.setEventListeners();
}


document.addEventListener("DOMContentLoaded", () => {
	initConfig();
});