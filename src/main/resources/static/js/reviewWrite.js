var DataObj = {
	displayInfoId: ServerDataObj.displayInfoId,
	responseData: null,

	loadResponseData: function(callback) {
		var httpRequest = new XMLHttpRequest();

		httpRequest.addEventListener("load", () => {
			DataObj.responseData = JSON.parse(httpRequest.responseText);

			callback();
		});
		httpRequest.open("GET", "api/products/{displayInfoId}".replace("{displayInfoId}", DataObj.displayInfoId));
		httpRequest.send();
	}
}

var CommentParam = {
	attachedImage: null,
	comment: "",
	productId: null,
	reservationInfoId: null,
	score: 0,
	
	postComment: function() {
		this.productId = DataObj.responseData.displayInfo.productId;
		this.reservationInfoId = ServerDataObj.reservationInfoId;
		
		if (this.score == 0 || !(/^.{5,400}$/).test(this.comment)) {
			return;
		}
		
		var httpRequest = new XMLHttpRequest();

		httpRequest.addEventListener("load", () => {
			window.location.href = "myreservation"; // 한줄평 완료시 예약확인 페이지로 돌아감
		});
		httpRequest.open("POST", "api/reservations/{reservationInfoId}/comments".replace("{reservationInfoId}", this.reservationInfoId));
		
		var formData = new FormData;
		formData.append("comment", this.comment);
		formData.append("productId", this.productId);
		formData.append("score", this.score);
		if (this.attachedImage) {
			formData.append("attachedImage", this.attachedImage);
		}
		httpRequest.send(formData);
	}
}

function RatingObj(element, score) {
	this._element = element;
	this._score = score;
	this._initObj();
}

RatingObj.prototype = {
	_ratingElement: {},
	_MAX_SCORE: 5,
	_initObj: function() {
		this._ratingElement[this._score] = this._element;
		this._element.addEventListener("click", () => {
			document.querySelector(".star_rank").innerText = this._score;
			CommentParam.score = this._score;
			CommonClassEditObj.removeClass(document.querySelector(".star_rank"), "gray_star");
			for (var i = 1; i <= this._MAX_SCORE; i++) {
				if (i <= this._score) {
					CommonClassEditObj.addClass(this._ratingElement[i], "checked");
				} else {
					CommonClassEditObj.removeClass(this._ratingElement[i], "checked");
				}
				
			}
		});
	}
}

var ShowObj = {
	showDetailInformation: function() {
		document.querySelector(".title").innerText = DataObj.responseData.displayInfo.productDescription;
		
		var ratingElements = document.querySelectorAll(".rating_rdo");
		for (var i = 0; i < ratingElements.length; i++) {
			new RatingObj(ratingElements[i], Number(ratingElements[i].value));
		}
	}
}

var EventObj = {
	setEventListeners: function() {
		// 한줄평 입력 부분
		document.querySelector(".review_write_info").addEventListener("click", (evt) => {
			evt.currentTarget.style.display = "none";
			document.querySelector(".review_textarea").focus();
		});
		document.querySelector(".review_textarea").addEventListener("keyup", (evt) => {
			var comment = evt.currentTarget.value;
			if (comment.length > 400) {
				comment = comment.substring(0, 400);
				evt.currentTarget.value = comment;
			}
			document.querySelector(".guide_review > span").innerText = comment.length;
			CommentParam.comment = comment;
		});
		document.querySelector(".review_textarea").addEventListener("focusout", (evt) => {
			if (evt.currentTarget.value === "") {
				document.querySelector(".review_write_info").style.display = "block";
			}
		});
		
		// 이미지 썸네일 표시
		document.querySelector("#reviewImageFileOpenInput").addEventListener("change", (evt) => {
			var imageFile = evt.target.files[0];
			if (imageFile.type !== "image/jpeg" && imageFile.type !== "image/png") {
				return;
			}
			document.querySelector(".lst_thumb > li").style.display = "inline-block";
			document.querySelector(".lst_thumb img").src = window.URL.createObjectURL(imageFile);
			CommentParam.attachedImage = imageFile;
		});
		// 이미지 썸네일 삭제버튼 클릭
		document.querySelector(".ico_del").addEventListener("click", () => {
			document.querySelector(".lst_thumb > li").style.display = "none";
			CommentParam.attachedImage = null;
		});
		
		// 리뷰 등록
		document.querySelector(".bk_btn").addEventListener("click", () => {
			CommentParam.postComment();
		});
	}
}

function initConfig() {
	DataObj.loadResponseData(() => ShowObj.showDetailInformation());
	EventObj.setEventListeners();
}


document.addEventListener("DOMContentLoaded", () => {
	initConfig();
});
