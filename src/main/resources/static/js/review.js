var ShowObj = {
	_displayInfoId: -1,
	_responseData: null,
	_showComment: function() {
		var commentData = this._responseData.comments;
		var templateWithImage = document.querySelector("#commentList").innerHTML.replace("[productDescription]", this._responseData.displayInfo.productDescription);
		var bindTemplateWithImage = Handlebars.compile(templateWithImage);
		var templateWithNoImage = document.querySelector("#commentListNoImage").innerHTML.replace("[productDescription]", this._responseData.displayInfo.productDescription);
		var bindTemplateWithNoImage = Handlebars.compile(templateWithNoImage);
		var parent = document.querySelector(".list_short_review");

		Handlebars.registerHelper("dateFormat", function(reservationDate) {
			return reservationDate.substring(0, 10).replaceAll("-", ".");
		});

		Handlebars.registerHelper("showFirstImageFileName", function(commentImages) {
			return commentImages[0].saveFileName;
		});

		document.querySelector(".grade_area .graph_value").style.width = (this._responseData.averageScore / 5.0) * 100 + "%";
		document.querySelector(".grade_area > .text_value > span").innerText = this._responseData.averageScore.toFixed(1);
		document.querySelector(".grade_area > .join_count > .green").innerText = this._responseData.comments.length + "건";

		for (var i = 0; i < commentData.length; i++) { // 코멘트를 모두 보여줌
			commentData[i].score = commentData[i].score.toFixed(1); // score값이 정수로 저장된 것을 소수점으로 나타내게 함
			if (commentData[i].commentImages.length > 0) { // 코멘트 이미지가 있는 경우의 처리
				parent.innerHTML += bindTemplateWithImage(commentData[i]);
			} else { // 코멘트 이미지가 없는 경우의 처리
				parent.innerHTML += bindTemplateWithNoImage(commentData[i]);
			}
		}
	},
	loadResponseData: function() {
		var httpRequest = new XMLHttpRequest();

		this._displayInfoId = Number(new URLSearchParams(location.search).get("id"));

		httpRequest.addEventListener("load", () => {
			this._responseData = JSON.parse(httpRequest.responseText);

			document.querySelector(".title").innerText = this._responseData.displayInfo.productDescription;
			this._showComment();
		});
		httpRequest.open("GET", "api/products/{displayInfoId}".replace("{displayInfoId}", this._displayInfoId));
		httpRequest.send();
	}
}

var EventObj = {
	setEventListeners: function() {
		document.querySelector(".btn_back").href = "./detail?id=" + new URLSearchParams(location.search).get("id");
	}
}

function initConfig() {
	ShowObj.loadResponseData();
	EventObj.setEventListeners();
}


document.addEventListener("DOMContentLoaded", () => {
	initConfig();
});
