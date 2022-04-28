var ShowObj = {
	_displayInfoId: -1,
	_responseData: null,
	_showDetailInformation: function() {
		this._showTitleImage();
		this._showDescription();
		this._showComment();
		this._showPath();
	},
	_showTitleImage: function() {
		var titleImageData = this._responseData.productImages;
		var template = document.querySelector("#titleImageList").innerHTML;
		var bindTemplate = Handlebars.compile(template);
		var parent = document.querySelector(".container_visual > .visual_img");
		
		ChangeObj.currentTitleImageIdx = 1;
		document.querySelector(".figure_pagination > .num").innerText = 1;
		ChangeObj.totalTitleImage = titleImageData.length;
		document.querySelector(".figure_pagination > .off > span").innerText = titleImageData.length;
		
		if (titleImageData.length === 1) { // 이미지가 1개뿐이면 화살표 숨김, 이미지 <li> 한 개만 추가하면 됨
			CommonClassEditObj.addClass(document.querySelector(".prev"), "hide");
			CommonClassEditObj.addClass(document.querySelector(".nxt"), "hide");
			parent.innerHTML += bindTemplate(titleImageData[0]);
		} else { // 이미지가 2개 이상일 경우 화살표는 그대로 두되 무한 슬라이딩을 위해 양옆에 복제본을 추가함
			parent.innerHTML += bindTemplate(titleImageData[titleImageData.length - 1]);
			for (var i = 0; i < titleImageData.length; i++) {
				parent.innerHTML += bindTemplate(titleImageData[i]);
			}
			parent.innerHTML += bindTemplate(titleImageData[0]);
			document.querySelector(".detail_swipe").style.left = - document.querySelector(".detail_swipe > .item").offsetWidth + "px";
		}
		
		document.querySelectorAll(".visual_txt_tit > span").forEach(function(spanTag) {
			spanTag.innerText = this._responseData.displayInfo.productDescription;
		}.bind(this))
	},
	_showDescription: function() {
		document.querySelector(".store_details > .dsc").innerText = this._responseData.displayInfo.productContent;
		document.querySelector(".detail_info_lst > .in_dsc").innerText = this._responseData.displayInfo.productContent;
	},
	_showComment: function() {
		var commentData = this._responseData.comments;
		var templateWithImage = document.querySelector("#commentList").innerHTML.replace("[productDescription]", this._responseData.displayInfo.productDescription);
		var bindTemplateWithImage = Handlebars.compile(templateWithImage);
		var templateWithNoImage = document.querySelector("#commentListNoImage").innerHTML.replace("[productDescription]", this._responseData.displayInfo.productDescription);
		var bindTemplateWithNoImage = Handlebars.compile(templateWithNoImage);
		var parent = document.querySelector(".list_short_review");
		
		Handlebars.registerHelper("dateFormat", function(reservationDate) {
			return reservationDate.substring(0, 10).replaceAll("-",".");
		});
		
		Handlebars.registerHelper("showFirstImageFileName", function(commentImages) {
			return commentImages[0].saveFileName;
		});
		
		document.querySelector(".grade_area .graph_value").style.width = (this._responseData.averageScore/5.0) * 100 + "%";
		document.querySelector(".grade_area > .text_value > span").innerText = this._responseData.averageScore.toFixed(1);
		document.querySelector(".grade_area > .join_count > .green").innerText = this._responseData.comments.length + "건";
		
		for (var i = 0; i < Math.min(commentData.length, 3); i++) { // 코멘트를 최대 3개까지 보여줌
			commentData[i].score = commentData[i].score.toFixed(1); // score값이 정수로 저장된 것을 소수점으로 나타내게 함
			if (commentData[i].commentImages.length > 0) { // 코멘트 이미지가 있는 경우의 처리
				parent.innerHTML += bindTemplateWithImage(commentData[i]);
			} else { // 코멘트 이미지가 없는 경우의 처리
				parent.innerHTML += bindTemplateWithNoImage(commentData[i]);
			}
		}
	},
	_showPath: function() {
		var template = document.querySelector("#path").innerHTML;
		var bindTemplate = Handlebars.compile(template);
		var parent = document.querySelector(".detail_location");
		parent.innerHTML += bindTemplate(this._responseData);
	},
	loadResponseData: function() {
		var httpRequest = new XMLHttpRequest();
		
		this._displayInfoId = Number(new URLSearchParams(location.search).get("id"));
		
		httpRequest.addEventListener("load", () => {
			this._responseData = JSON.parse(httpRequest.responseText);
			
			this._showDetailInformation();
		});
		httpRequest.open("GET", "api/products/{displayInfoId}".replace("{displayInfoId}", this._displayInfoId));
		httpRequest.send();
	}
}

var ChangeObj = {
	_currentTabIdx: 1,
	currentTitleImageIdx: -1,
	totalTitleImage: -1,
	changeTitleImage: function(idxIncrement) {
		var slide = document.querySelector(".detail_swipe");
		var width = document.querySelector(".detail_swipe > .item").offsetWidth;
		
		this.currentTitleImageIdx += idxIncrement;
		
		slide.style.transition = "all 0.25s"
		slide.style.left = - (width * this.currentTitleImageIdx) + "px";
		
		if (this.currentTitleImageIdx < 1) { // 맨 왼쪽에 넣은 복사본에 도달한 경우
			setTimeout(() => {
				slide.style.transition = "0s";
				this.currentTitleImageIdx = this.totalTitleImage;
				slide.style.left = - (width * this.currentTitleImageIdx) + "px";
			}, 250);
		} else if (this.currentTitleImageIdx > this.totalTitleImage) { // 맨 오른쪽에 넣은 복사본에 도달한 경우
			setTimeout(() => {
				slide.style.transition = "0s";
				this.currentTitleImageIdx = 1;
				slide.style.left = - (width * this.currentTitleImageIdx) + "px";
			}, 250);
		}
		
		// 현재 보여주는 페이지 번호에 맞춰 번호를 바꾸고 왼쪽/오른쪽 화살표를 활성화
		if (this.currentTitleImageIdx == 1 || this.currentTitleImageIdx > this.totalTitleImage) {
			document.querySelector(".figure_pagination > .num").innerText = 1;
			CommonClassEditObj.addClass(document.querySelector(".ico_arr6_lt"), "off");
			CommonClassEditObj.removeClass(document.querySelector(".ico_arr6_rt"), "off");
		} else if (this.currentTitleImageIdx == this.totalTitleImage || this.currentTitleImageIdx < 1) {
			document.querySelector(".figure_pagination > .num").innerText = this.totalTitleImage;
			CommonClassEditObj.addClass(document.querySelector(".ico_arr6_rt"), "off");
			CommonClassEditObj.removeClass(document.querySelector(".ico_arr6_lt"), "off");
		} else {
			document.querySelector(".figure_pagination > .num").innerText = this.currentTitleImageIdx;
			CommonClassEditObj.removeClass(document.querySelector(".ico_arr6_lt"), "off");
			CommonClassEditObj.removeClass(document.querySelector(".ico_arr6_rt"), "off");
		}
		
	},
	changeTab: function(newTabIdx) {
		if (this._currentTabIdx == newTabIdx) {
			return;
		}
		
		this._currentTabIdx = newTabIdx;
		if (newTabIdx == 1) { // 상세정보 선택
			CommonClassEditObj.addClass(document.querySelector(".info_tab_lst > ._detail > .anchor"), "active");
			CommonClassEditObj.removeClass(document.querySelector(".info_tab_lst > ._path > .anchor"), "active");
			CommonClassEditObj.addClass(document.querySelector(".detail_location"), "hide");
			CommonClassEditObj.removeClass(document.querySelector(".detail_area_wrap"), "hide");			
		} else if (newTabIdx == 2) {
			CommonClassEditObj.removeClass(document.querySelector(".info_tab_lst > ._detail > .anchor"), "active");
			CommonClassEditObj.addClass(document.querySelector(".info_tab_lst > ._path > .anchor"), "active");
			CommonClassEditObj.removeClass(document.querySelector(".detail_location"), "hide");
			CommonClassEditObj.addClass(document.querySelector(".detail_area_wrap"), "hide");	
		}
	}
}

var EventObj = {
	setEventListeners: function() {
		CommonEventObj.setEventListeners();
		
		document.querySelector("._open").addEventListener("click", () => {
			document.querySelector("._open").style.display = "none";
			document.querySelector("._close").style.display = "block";
			CommonClassEditObj.removeClass(document.querySelector(".store_details"), "close3");
		});
		document.querySelector("._close").addEventListener("click", () => {
			document.querySelector("._close").style.display = "none";
			document.querySelector("._open").style.display = "block";
			CommonClassEditObj.addClass(document.querySelector(".store_details"), "close3");
		});
		document.querySelector(".prev").addEventListener("click", () => {
			ChangeObj.changeTitleImage(-1);
		});
		document.querySelector(".nxt").addEventListener("click", () => {
			ChangeObj.changeTitleImage(1);
		});
		document.querySelector(".info_tab_lst > ._detail").addEventListener("click", () => {
			ChangeObj.changeTab(1);
		});
		document.querySelector(".info_tab_lst > ._path").addEventListener("click", () => {
			ChangeObj.changeTab(2);
		});
		document.querySelector(".btn_review_more").href = "./review?id=" + new URLSearchParams(location.search).get("id");
	}
}

function initConfig() {
	ShowObj.loadResponseData();
	EventObj.setEventListeners();
}


document.addEventListener("DOMContentLoaded", () => {
	initConfig();
});