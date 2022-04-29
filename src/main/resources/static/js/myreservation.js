var DataObj = {
	responseData: null,
	reservationEmail: null, // 서버 session에서 가져와야 함
	confirmedReservation: [], // 이용예정인 예약정보 리스트
	usedReservation: [], // 이용완료인 예약정보 리스트
	canceledReservation: [], // 취소된 예약정보 리스트

	loadResponseData: function(callback) {
		var httpRequest = new XMLHttpRequest();

		httpRequest.addEventListener("load", () => {
			this.responseData = JSON.parse(httpRequest.responseText);

			for (var i = 0; i < this.responseData.reservations.length; i++) { // cancelYn 유무로 취소된 예약정보인지 아닌지 확인
				if (this.responseData.reservations[i].cancelYn) {
					this.canceledReservation.push(this.responseData.reservations[i]);
				} else {
					/*
						프로젝트에서 주어진 데이터베이스에서는 이용예정인 예약정보와 이용완료인 예약정보를 구별하도록 하는 별도의 정보가 없었음
						또한 이용예정인 정보를 이용완료를 바꾸는 별도의 방법도 제시되지 않아서, 우선 예약조회 시점 기준 날짜가 지난 것들을 이용완료에 넣는 방식을 취함
					*/
					if (new Date > new Date(this.responseData.reservations[i].reservationDate + " 23:59:59")) { // 예약 날짜가 이미 지난 경우 이용완료로 처리
						this.usedReservation.push(this.responseData.reservations[i]);
					} else {
						this.confirmedReservation.push(this.responseData.reservations[i]);
					}
				}
			}

			callback();
		});
		this.reservationEmail = document.querySelector("#reservation_email").innerText;
		httpRequest.open("GET", "api/reservations?reservationEmail={reservationEmail}".replace("{reservationEmail}", DataObj.reservationEmail));
		httpRequest.send();
	}
}

var ShowObj = {
	_currentTab: 0,
	_confirmedCount: 0,
	_usedCount: 0,
	_canceledCount: 0,
	_updateReservationCount: function() {
		this._confirmedCount = DataObj.confirmedReservation.length;
		this._usedCount = DataObj.usedReservation.length;
		this._canceledCount = DataObj.canceledReservation.length;
	},
	_showSummaryBoard: function() {
		document.querySelector("#total_reservation_count").innerText = this._confirmedCount + this._usedCount + this._canceledCount;
		document.querySelector("#confirmed_reservation_count").innerText = this._confirmedCount;
		document.querySelector("#used_reservation_count").innerText = this._usedCount;
		document.querySelector("#canceled_reservation_count").innerText = this._canceledCount;
	},
	_showCardList: function() {
		var template = document.querySelector("#card_item_list").innerHTML;
		var bindTemplate = Handlebars.compile(template);
		var confirmedParent = document.querySelector("#confirmed_reservation_list");
		var usedParent = document.querySelector("#used_reservation_list");
		var canceledParent = document.querySelector("#canceled_reservation_list");

		Handlebars.registerHelper("formattingDate", function(date) { // YYYY-M-D를 YYYY.M.D(요일)의 형식으로 바꾸기 위함
			var dayOfWeek = ["(일)", "(월)", "(화)", "(수)", "(목)", "(금)", "(토)"];
			return date.replaceAll("-", ".") + "." + dayOfWeek[new Date(date).getDay()];
		});
		Handlebars.registerHelper("formattingPrice", function(price) { // 금액을 세자리씩 끊기 위함
			return price.toLocaleString();
		});

		for (var i = 0; i < this._confirmedCount; i++) {
			confirmedParent.insertAdjacentHTML("beforeend", bindTemplate(DataObj.confirmedReservation[i]));
		}
		var confirmedCardBelow = confirmedParent.querySelectorAll(".booking_cancel"); // 예약 확정에서는 예매자 리뷰 남기기 버튼이 없어야 한다.
		for (var i = 0; i < confirmedCardBelow.length; i++) {
			var deleteTarget = confirmedCardBelow[i].querySelector("a");
			confirmedCardBelow[i].removeChild(deleteTarget);
		}

		for (var i = 0; i < this._usedCount; i++) {
			usedParent.insertAdjacentHTML("beforeend", bindTemplate(DataObj.usedReservation[i]));
		}
		var usedCardBelow = usedParent.querySelectorAll(".booking_cancel"); // 예약 확정에서는 취소 버튼이 없어야 한다.
		for (var i = 0; i < usedCardBelow.length; i++) {
			var deleteTarget = usedCardBelow[i].querySelector("button");
			usedCardBelow[i].removeChild(deleteTarget);
		}

		for (var i = 0; i < this._canceledCount; i++) {
			canceledParent.insertAdjacentHTML("beforeend", bindTemplate(DataObj.canceledReservation[i]));
		}
		var canceledCardBelow = canceledParent.querySelectorAll(".booking_cancel"); // 취소에서는 하단 버튼이 아예 없어야 한다.
		for (var i = 0; i < canceledCardBelow.length; i++) {
			canceledCardBelow[i].innerHTML = "";
		}

		this.changeTab(1); // 초기에는 1번(전체)탭 선택한 화면이 보임
	},
	changeTab: function(tabNumber) { // 순서대로 1번(전체), 2번(이용예정), 3번(이용완료), 4번(취소) 탭을 눌렀을 때 적용된다. 0 입력시 현재 탭 그대로 새로고침
		if (this._currentTab === tabNumber) {
			return;
		}
		if (tabNumber == 0) {
			tabNumber = this._currentTab;
		}
		this._currentTab = tabNumber;
		this._updateReservationCount();

		// 모든 탭에 불이 꺼져 있고 예약 리스트 없다는 창만 나와있는 상태에서 시작
		CommonClassEditObj.removeClass(document.querySelector("#total_reservation_tab"), "on");
		CommonClassEditObj.removeClass(document.querySelector("#confirmed_reservation_tab"), "on");
		CommonClassEditObj.removeClass(document.querySelector("#used_reservation_tab"), "on");
		CommonClassEditObj.removeClass(document.querySelector("#canceled_reservation_tab"), "on");
		CommonClassEditObj.addClass(document.querySelector("#confirmed_reservation_list"), "hide");
		CommonClassEditObj.addClass(document.querySelector("#used_reservation_list"), "hide");
		CommonClassEditObj.addClass(document.querySelector("#canceled_reservation_list"), "hide");
		CommonClassEditObj.removeClass(document.querySelector(".err"), "hide");

		if (tabNumber === 1) {
			CommonClassEditObj.addClass(document.querySelector("#total_reservation_tab"), "on");
		} else if (tabNumber === 2) {
			CommonClassEditObj.addClass(document.querySelector("#confirmed_reservation_tab"), "on");
		} else if (tabNumber === 3) {
			CommonClassEditObj.addClass(document.querySelector("#used_reservation_tab"), "on");
		} else if (tabNumber === 4) {
			CommonClassEditObj.addClass(document.querySelector("#canceled_reservation_tab"), "on");
		}

		if ((tabNumber === 1 || tabNumber === 2) && this._confirmedCount > 0) {
			CommonClassEditObj.removeClass(document.querySelector("#confirmed_reservation_list"), "hide");
			CommonClassEditObj.addClass(document.querySelector(".err"), "hide");
		}

		if ((tabNumber === 1 || tabNumber === 3) && this._usedCount > 0) {
			CommonClassEditObj.removeClass(document.querySelector("#used_reservation_list"), "hide");
			CommonClassEditObj.addClass(document.querySelector(".err"), "hide");
		}

		if ((tabNumber === 1 || tabNumber === 4) && this._canceledCount > 0) {
			CommonClassEditObj.removeClass(document.querySelector("#canceled_reservation_list"), "hide");
			CommonClassEditObj.addClass(document.querySelector(".err"), "hide");
		}

	},
	showDetailInformation: function() {
		this._updateReservationCount();
		this._showSummaryBoard();
		this._showCardList();
	},
	showReservationCancelPopup: function(reservationInfoId, title, reservationDate) {
		ReservationCancelObj.reservationInfoId = reservationInfoId;
		document.querySelector(".pop_tit > span").innerText = title;
		document.querySelector(".pop_tit > small").innerText = reservationDate;
		document.querySelector(".popup_booking_wrapper").style.display = "block";
		document.querySelector(".refund").focus(); // 탭키를 눌렀을 때 첫번째 버튼으로 focus에 오도록 함
	}
}

var ReservationCancelObj = {
	reservationInfoId: -1,
	cancelReservation: function() {
		var httpRequest = new XMLHttpRequest();

		httpRequest.addEventListener("load", () => {
			// DataObj에 있는 배열 수정
			for (var i = 0; i < DataObj.confirmedReservation.length; i++) {
				if (DataObj.confirmedReservation[i].reservationInfoId === this.reservationInfoId) {
					DataObj.confirmedReservation[i].cancelYn = true;
					DataObj.canceledReservation.push(DataObj.confirmedReservation[i]);
					DataObj.confirmedReservation.splice(i, 1);

					// 실제 보이는 내용 수정
					var confirmedBookingNumberList = document.querySelectorAll("#confirmed_reservation_list .booking_number");
					for (var i = 0; i < confirmedBookingNumberList.length; i++) {
						if (confirmedBookingNumberList[i].innerText === "No."+ this.reservationInfoId) {
							var moveElement = confirmedBookingNumberList[i].closest("article");
							var parent = document.querySelector("#canceled_reservation_list");
							moveElement.querySelector(".booking_cancel").innerHTML = "";
							parent.appendChild(moveElement);
							break;
						}
					}

					ShowObj.changeTab(0);
					document.querySelector("#confirmed_reservation_count").innerText = DataObj.confirmedReservation.length;
					document.querySelector("#canceled_reservation_count").innerText = DataObj.canceledReservation.length;

					break;
				}
			}
		});
		httpRequest.open("PUT", "api/reservations/{reservationInfoId}".replace("{reservationInfoId}", this.reservationInfoId));
		httpRequest.send();
	}
}

var EventObj = {
	setEventListeners: function() {
		document.querySelector("#total_reservation_tab").addEventListener("click", () => {
			ShowObj.changeTab(1);
		});
		document.querySelector("#confirmed_reservation_tab").addEventListener("click", () => {
			ShowObj.changeTab(2);
		});
		document.querySelector("#used_reservation_tab").addEventListener("click", () => {
			ShowObj.changeTab(3);
		});
		document.querySelector("#canceled_reservation_tab").addEventListener("click", () => {
			ShowObj.changeTab(4);
		});

		document.querySelector(".btn_gray > .btn_bottom").addEventListener("click", () => {
			document.querySelector(".popup_booking_wrapper").style.display = "none";
		});
		document.querySelector(".btn_green > .btn_bottom").addEventListener("click", () => {
			document.querySelector(".popup_booking_wrapper").style.display = "none";
			ReservationCancelObj.cancelReservation();
		});
		document.querySelector(".popup_btn_close").addEventListener("click", () => {
			document.querySelector(".popup_booking_wrapper").style.display = "none";
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