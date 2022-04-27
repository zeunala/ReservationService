var ProductObj = {
	_showCategoryId: -1, // 현재 보여주고 있는 카테고리 번호(초기값 -1, 전체목록은 0)
	_showItems: 0, // 현재 보여주고 있는 상품의 개수
	getShowCategoryId: function() {
		return this._showCategoryId;
	},
	showProduct: function(categoryId) {
		var httpRequest = new XMLHttpRequest();

		if (this._showCategoryId !== -1 && this._showCategoryId !== categoryId) { // 탭을 바꾸는 경우 기존 선택된 탭과 클래스명을 바꿔 초록색으로 표시된 부분을 바꾸고, 기존 표시된 내용 목록도 지움
			CommonClassEditObj.removeClass(document.querySelector(".item[data-category='" + this._showCategoryId + "'] > a"), "active");
			CommonClassEditObj.addClass(document.querySelector(".item[data-category='" + categoryId + "'] > a"), "active");
			document.querySelector("#left_box").innerHTML = "";
			document.querySelector("#right_box").innerHTML = "";
			this._showItems = 0;
		}

		httpRequest.addEventListener("load", () => {
			var data = JSON.parse(httpRequest.responseText);
			var template = document.querySelector("#itemList").innerHTML;
			var bindTemplate = Handlebars.compile(template);
			
			document.querySelector(".event_lst_txt > .pink").innerText = data.totalCount + "개";

			for (var i = 0; i < data.items.length; i++) {
				if (i % 2 === 0) {
					var parent = document.querySelector("#left_box");
					parent.innerHTML += bindTemplate(data.items[i]);
				} else {
					var parent = document.querySelector("#right_box");
					parent.innerHTML += bindTemplate(data.items[i]);
				}
			}
			this._showCategoryId = categoryId;
			this._showItems += data.items.length;
			if (data.totalCount <= this._showItems) { // 목록을 다 보여준 경우 더보기버튼이 안보이게 함
				document.querySelector(".more > button").style.display = "none";
			} else {
				document.querySelector(".more > button").style.display = "inline-block";
			}

		});
		httpRequest.open("GET", "api/products?categoryId={id}&start={start}".replace("{id}", categoryId).replace("{start}", this._showItems));
		httpRequest.send();
	}
}

var PromotionObj = {
	_totalSlide: 0, // 슬라이드에서의 총 프로모션 수
	_slideIdx: 0, // 현재 보여지고 있는 슬라이드 인덱스
	_lastAnimationTimestamp: null, // requestAnimationFrame의 주기 조절 위함(마지막 화면전환 당시의 시각 기록)
	_showSlide: function(timestamp) {
		if (!this._lastAnimationTimestamp) {
			this._lastAnimationTimestamp = timestamp;
		}

		if (timestamp - this._lastAnimationTimestamp >= 2000) {
			var slide = document.querySelector(".visual_img");
			var width = document.querySelector(".visual_img > .item").offsetWidth;

			slide.style.transition = "all 0.25s"
			slide.style.left = - (width * this._slideIdx) + "px";

			this._slideIdx += 1;
			if (this._slideIdx > this._totalSlide) { // 마지막 이미지 뒤에 있는 첫번째 이미지(복제본)까지 다 보여주었다면 맨 처음 이미지로 순간이동하고 다음으로 두번째 이미지를 보여주도록 한다.
				setTimeout(() => {
					slide.style.transition = "0s"
					slide.style.left = "0px";
					this._slideIdx = 1;
				}, 250);
			}

			this._lastAnimationTimestamp = timestamp;
		}
		requestAnimationFrame(this._showSlide.bind(this));
	},
	showPromotion: function() {
		var httpRequest = new XMLHttpRequest();

		httpRequest.addEventListener("load", () => {
			var data = JSON.parse(httpRequest.responseText);
			var template = document.querySelector("#promotionItem").innerHTML;
			var bindTemplate = Handlebars.compile(template);
			var parent = document.querySelector(".visual_img");

			for (var i = 0; i < data.items.length; i++) {
				parent.innerHTML += bindTemplate(data.items[i]);
			}
			parent.innerHTML += bindTemplate(data.items[0]); // 무한 슬라이드를 위해 마지막 이미지 뒤에 첫 이미지를 중복해서 붙여넣는다.

			this._totalSlide = data.items.length;
			requestAnimationFrame(this._showSlide.bind(this));
		});
		httpRequest.open("GET", "api/promotions");
		httpRequest.send();
	}
}

var EventObj = {
	setEventListeners: function() {
		CommonEventObj.setEventListeners();

		document.querySelector(".more > button").addEventListener("click", () => {
			ProductObj.showProduct(ProductObj.getShowCategoryId()); // 선택한 탭 그대로인 상태에서 목록만 추가
		});
		document.querySelector(".section_event_tab").addEventListener("click", (evt) => {
			var parent = evt.target.closest("li");
			if (parent !== null && parent.className === "item") { // ul태그를 클릭한게 아니라 그 안에 있는 li나 a태그 등을 클릭한 경우
				clickCategory = Number(parent.getAttribute("data-category"));
				if (ProductObj.getShowCategoryId() !== clickCategory) {
					ProductObj.showProduct(clickCategory);
				}
			}
		});
	}
}

function initConfig() {
	ProductObj.showProduct(0);
	PromotionObj.showPromotion();
	EventObj.setEventListeners();
}

document.addEventListener("DOMContentLoaded", () => {
	initConfig();
});
