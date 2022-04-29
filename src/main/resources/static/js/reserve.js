function TicketPriceSection(productPriceId, priceTypeName, price, discountRate) {
	this.productPriceId = productPriceId;
	this.priceTypeName = priceTypeName;
	this.priceTypeNameKorean = this._priceTypeNameMappingDict[priceTypeName]; // API에서 알파벳으로 나타낸 것을 한글로 변환하여 넣는다.
	this.price = price;
	this.discountRate = discountRate;
	this.count = 0;
	this.htmlBody = undefined;

	this._displaySection();
}

TicketPriceSection.prototype = {
	_totalCount: 0,
	allElements: [], // 모든 TicketPriceSection 객체들을 저장
	_priceTypeNameMappingDict: {
		A: "성인", Y: "청소년", B: "유아", D: "장애인", C: "지역주민", E: "어얼리버드",
		V: "VIP", R: "R석", S: "S석"
	},
	_displaySection: function() {
		var template = document.querySelector("#ticket_list").innerHTML;
		var bindTemplate = Handlebars.compile(template);
		var parent = document.querySelector(".ticket_body");

		Handlebars.registerHelper("formattingPrice", function(price) { // 금액을 세자리씩 끊기 위함
			return price.toLocaleString();
		});

		parent.insertAdjacentHTML("beforeend", bindTemplate(this));
		this.htmlBody = parent.querySelector("#ticket_" + this.priceTypeName);

		this.htmlBody.querySelector(".ico_minus3").addEventListener("click", () => {
			this._decreaseCount();
		});
		this.htmlBody.querySelector(".ico_plus3").addEventListener("click", () => {
			this._increaseCount();
		});

		TicketPriceSection.prototype.allElements.push(this);
	},
	_increaseCount: function() {
		this.count += 1;
		TicketPriceSection.prototype._totalCount += 1;
		this._completeCountChange();
	},
	_decreaseCount: function() {
		if (this.count <= 0) {
			return;
		}

		this.count -= 1;
		TicketPriceSection.prototype._totalCount -= 1;
		this._completeCountChange();
	},
	_completeCountChange: function() { // count 변경 후처리(실제 보여지는 부분들을 바꿈)
		this.htmlBody.querySelector("input").value = this.count;
		this.htmlBody.querySelector(".total_price").innerText = (this.count * this.price).toLocaleString();
		document.querySelector("#total_count").innerText = TicketPriceSection.prototype._totalCount;

		if (this.count == 0) {
			CommonClassEditObj.addClass(this.htmlBody.querySelector(".ico_minus3"), "disabled");
			CommonClassEditObj.addClass(this.htmlBody.querySelector(".count_control_input"), "disabled");
			CommonClassEditObj.removeClass(this.htmlBody.querySelector(".individual_price"), "on_color");
		} else {
			CommonClassEditObj.removeClass(this.htmlBody.querySelector(".ico_minus3"), "disabled");
			CommonClassEditObj.removeClass(this.htmlBody.querySelector(".count_control_input"), "disabled");
			CommonClassEditObj.addClass(this.htmlBody.querySelector(".individual_price"), "on_color");
		}

		if (TicketPriceSection.prototype._totalCount > 0) {
			FormCheckObj.countValid = true;
		} else {
			FormCheckObj.countValid = false;
		}
	}
}

var DataObj = {
	displayInfoId: Number(new URLSearchParams(location.search).get("id")),
	responseData: null,
	reservationDate: null,

	loadResponseData: function(callback) {
		var httpRequest = new XMLHttpRequest();

		httpRequest.addEventListener("load", () => {
			DataObj.responseData = JSON.parse(httpRequest.responseText);

			callback();
		});
		httpRequest.open("GET", "api/products/{displayInfoId}".replace("{displayInfoId}", DataObj.displayInfoId));
		httpRequest.send();
	},
	loadReservationDate: function() {
		var httpRequest = new XMLHttpRequest();

		httpRequest.addEventListener("load", () => {
			DataObj.reservationDate = JSON.parse(httpRequest.responseText).reservationDate;

			document.querySelector("#reservation_date").innerText = DataObj.reservationDate;
		});
		httpRequest.open("GET", "api/product-reservation-date/{displayInfoId}".replace("{displayInfoId}", DataObj.displayInfoId));
		httpRequest.send();
	}
}

function ReservationParam() {
	this.displayInfoId = DataObj.responseData.displayInfo.displayInfoId;
	this.prices = [];
	for (var i = 0; i < TicketPriceSection.prototype.allElements.length; i++) {
		if (TicketPriceSection.prototype.allElements[i].count == 0) { // 0개 예매한 항목은 추가하지 않음
			continue;
		}

		var priceObj = {
			count: TicketPriceSection.prototype.allElements[i].count,
			productPriceId: TicketPriceSection.prototype.allElements[i].productPriceId,
			reservationInfoId: 0, // 서버에서 자동으로 채워줄 부분
			reservationInfoPriceId: 0 // 서버에서 자동으로 채워줄 부분
		}
		this.prices.push(priceObj);
	}

	this.productId = DataObj.responseData.displayInfo.productId;
	this.reservationEmail = document.querySelector("#email").value;
	this.reservationName = document.querySelector("#name").value;
	this.reservationTelephone = document.querySelector("#tel").value;
	this.reservationYearMonthDay = DataObj.reservationDate;

	this._postReservation();
}

ReservationParam.prototype = {
	_postReservation: function() {
		if (FormCheckObj.checkTotalFormValid() == false) {
			return;
		}

		var httpRequest = new XMLHttpRequest();

		httpRequest.addEventListener("load", () => {
			window.location.href = "myreservation"; // 예약 완료시 예약확인 페이지로 들어감
		});
		httpRequest.open("POST", "api/reservations");
		httpRequest.setRequestHeader("Content-Type", "application/json");
		httpRequest.send(JSON.stringify(this));
	}
}

var FormCheckObj = {
	countValid: false,
	telValid: false,
	emailValid: false,
	agreeValid: false,

	checkTelValid: function(value) {
		if ((/^0\d{1,2}-\d{3,4}-\d{4}$/).test(value)) { // ex. 02-123-4567, 010-1234-5678
			this.telValid = true;
			CommonClassEditObj.addClass(document.querySelector("#invalid_tel_alert"), "hide");
		} else {
			this.telValid = false;
			CommonClassEditObj.removeClass(document.querySelector("#invalid_tel_alert"), "hide");
		}

	},
	checkEmailValid: function(value) {
		if ((/^[\w+_]\w+@\w+\.(\w+\.)?\w+$/).test(value)) { // ex. sample@naver.com, sample@yahoo.co.kr
			this.emailValid = true;
			CommonClassEditObj.addClass(document.querySelector("#invalid_email_alert"), "hide");
		} else {
			this.emailValid = false;
			CommonClassEditObj.removeClass(document.querySelector("#invalid_email_alert"), "hide");
		}
	},
	checkTotalFormValid: function() {
		return this.countValid && this.telValid && this.emailValid && this.agreeValid;
	}
}

var ShowObj = {
	_showDescription: function() {
		for (var i = 0; i < DataObj.responseData.productImages.length; i++) {
			if (DataObj.responseData.productImages[i].type === "ma") {
				document.querySelector(".img_thumb").src = DataObj.responseData.productImages[i].saveFileName;
				break;
			}
		}
		document.querySelector(".preview_txt_dsc").innerText = DataObj.responseData.displayInfo.productDescription;
		document.querySelector("#place-name").innerText = DataObj.responseData.displayInfo.placeName;
		document.querySelector("#opening-hours").innerText = DataObj.responseData.displayInfo.openingHours;
	},
	_showTicketList: function() {
		var productPrices = DataObj.responseData.productPrices;
		productPrices.sort((a, b) => b.price - a.price); // 가격 내림차순으로 정렬
		for (var i = 0; i < productPrices.length; i++) {
			new TicketPriceSection(productPrices[i].productPriceId, productPrices[i].priceTypeName, productPrices[i].price, productPrices[i].discountRate);
		}
	},
	showDetailInformation: function() {
		this._showDescription();
		this._showTicketList();
	}
}

var EventObj = {
	setEventListeners: function() {
		document.querySelector(".tel_wrap > input").addEventListener("change", (evt) => {
			FormCheckObj.checkTelValid(evt.target.value);
		});

		document.querySelector(".email_wrap > input").addEventListener("change", (evt) => {
			FormCheckObj.checkEmailValid(evt.target.value);
		});

		var agreementElementArr = document.querySelectorAll(".btn_agreement");
		for (var i = 0; i < agreementElementArr.length; i++) {
			agreementElementArr[i].addEventListener("click", (evt) => {
				if (evt.currentTarget.parentElement.classList.contains("open")) { // 약관이 열려있을 경우(보기상태)
					CommonClassEditObj.removeClass(evt.currentTarget.parentElement, "open");

					evt.currentTarget.querySelector("span").innerText = "보기";
					CommonClassEditObj.addClass(evt.currentTarget.querySelector("i"), "fn-down2"); // 아래 화살표로 변경
					CommonClassEditObj.removeClass(evt.currentTarget.querySelector("i"), "fn-up2");
				} else {
					CommonClassEditObj.addClass(evt.currentTarget.parentElement, "open");

					evt.currentTarget.querySelector("span").innerText = "접기";
					CommonClassEditObj.addClass(evt.currentTarget.querySelector("i"), "fn-up2");
					CommonClassEditObj.removeClass(evt.currentTarget.querySelector("i"), "fn-down2");
				}

			});
		}

		document.querySelector(".chk_agree").addEventListener("change", (evt) => {
			if (evt.target.checked) { // 이용자약관 전체동의 체크
				FormCheckObj.agreeValid = true;
				/* 
					예약하기 버튼 활성화 되는 조건이 약관정보 동의에 체크할 때인지, 모든 정보가 유효할 때 인지 평가기준이 불명확하여
					우선 제공된 html코드에 적혀있는대로 약관전체동의시 예약버튼이 활성화되도록 함 (실제 작동은 모든 정보가 유효할 때 동작함)
				*/
				CommonClassEditObj.removeClass(document.querySelector(".box_bk_btn > .bk_btn_wrap"), "disable");
			} else {
				FormCheckObj.agreeValid = false;
				CommonClassEditObj.addClass(document.querySelector(".box_bk_btn > .bk_btn_wrap"), "disable");
			}
		});

		document.querySelector(".bk_btn").addEventListener("click", () => {
			new ReservationParam();
		});
	}
}

function initConfig() {
	DataObj.loadResponseData(() => ShowObj.showDetailInformation());
	DataObj.loadReservationDate();
	EventObj.setEventListeners();
}


document.addEventListener("DOMContentLoaded", () => {
	initConfig();
});