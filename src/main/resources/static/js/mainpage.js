var showCategoryId = -1; // 현재 보여주고 있는 카테고리 번호(초기값 -1, 전체목록은 0)
var showItems = 0; // 현재 보여주고 있는 상품의 개수

var totalSlide = 0; // 슬라이드에서의 총 프로모션 수
var slideIdx = 0; // 현재 보여지고 있는 슬라이드 인덱스

var lastAnimationTimestamp = null; // requestAnimationFrame의 주기 조절 위함(마지막 화면전환 당시의 시각 기록)

function showProduct(categoryId) {
	var httpRequest = new XMLHttpRequest();

	if (showCategoryId !== -1 && showCategoryId !== categoryId) { // 탭을 바꾸는 경우 기존 선택된 탭과 클래스명을 바꿔 초록색으로 표시된 부분을 바꾸고, 기존 표시된 내용 목록도 지움
		document.querySelector(".item[data-category='" + showCategoryId + "'] > a").className = "anchor";
		document.querySelector(".item[data-category='" + categoryId + "'] > a").className = "anchor active";
		document.querySelector("#left_box").innerHTML = "";
		document.querySelector("#right_box").innerHTML = "";
		showItems = 0;
	}

	httpRequest.addEventListener("load", function() {
		var data = JSON.parse(httpRequest.responseText);
		var html = document.querySelector("#itemList").innerHTML
		document.querySelector(".event_lst_txt > .pink").innerText = data.totalCount + "개";

		for (var i = 0; i < data.items.length; i++) {
			if (i % 2 === 0) {
				var parent = document.querySelector("#left_box");
				parent.innerHTML += html.replaceAll("{productDescription}", data.items[i].productDescription)
					.replace("{productImageUrl}", data.items[i].productImageUrl)
					.replace("{placeName}", data.items[i].placeName)
					.replace("{productContent}", data.items[i].productContent)
			} else {
				var parent = document.querySelector("#right_box");
				parent.innerHTML += html.replaceAll("{productDescription}", data.items[i].productDescription)
					.replace("{productImageUrl}", data.items[i].productImageUrl)
					.replace("{placeName}", data.items[i].placeName)
					.replace("{productContent}", data.items[i].productContent)
			}
		}
		showCategoryId = categoryId;
		showItems += data.items.length;
		if (data.totalCount <= showItems) { // 목록을 다 보여준 경우 더보기버튼이 안보이게 함
			document.querySelector(".more > button").style.display = "none";
		} else {
			document.querySelector(".more > button").style.display = "inline-block";
		}

	});
	httpRequest.open("GET", "api/products?categoryId={id}&start={start}".replace("{id}", categoryId).replace("{start}", showItems));
	httpRequest.send();

}

function showSlide(timestamp) {
	if (!lastAnimationTimestamp) {
		lastAnimationTimestamp = timestamp;
	}

	if (timestamp - lastAnimationTimestamp >= 2000) {
		var slide = document.querySelector(".visual_img");
		var width = document.querySelector(".visual_img > .item").offsetWidth;
		
		slide.style.transition = "all 0.25s"
		slide.style.left = - (width * slideIdx) + "px";
	
		slideIdx += 1;
		if (slideIdx > totalSlide) { // 마지막 이미지 뒤에 있는 첫번째 이미지(복제본)까지 다 보여주었다면 맨 처음 이미지로 순간이동하고 다음으로 두번째 이미지를 보여주도록 한다.
			setTimeout(() => {
				slide.style.transition = "0s"
				slide.style.left = "0px";
				slideIdx = 1;
			},250);
		}
		
		lastAnimationTimestamp = timestamp;
	}
	requestAnimationFrame(showSlide);
}

function showPromotion() {
	var httpRequest = new XMLHttpRequest();

	httpRequest.addEventListener("load", function() {
		var data = JSON.parse(httpRequest.responseText);
		var html = document.querySelector("#promotionItem").innerHTML;
		var parent = document.querySelector(".visual_img");
		
		for (var i = 0; i < data.items.length; i++) {
			parent.innerHTML += html.replace("{productImageUrl}", data.items[i].productImageUrl);
		}
		parent.innerHTML += html.replace("{productImageUrl}", data.items[0].productImageUrl); // 무한 슬라이드를 위해 마지막 이미지 뒤에 첫 이미지를 중복해서 붙여넣는다.
		
		totalSlide = data.items.length;
		requestAnimationFrame(showSlide);
	});
	httpRequest.open("GET", "api/promotions");
	httpRequest.send();
}

function init() {
	showProduct(0);
	showPromotion();
	document.querySelector(".more > button").addEventListener("click", function() {
		showProduct(showCategoryId); // 선택한 탭 그대로인 상태에서 목록만 추가
	});
	document.querySelector(".section_event_tab").addEventListener("click", function(evt) {
		var parent = evt.target.closest("li");
		if (parent !== null && parent.className === "item") { // ul태그를 클릭한게 아니라 그 안에 있는 li나 a태그 등을 클릭한 경우
			clickCategory = Number(parent.getAttribute("data-category"));
			if (showCategoryId !== clickCategory) {
				showProduct(clickCategory);
			}
		}
	});
	document.querySelector(".gototop").addEventListener("click", function() {
		window.scrollTo({top:0, left:0, behavior:'smooth'});
	});
}

document.addEventListener("DOMContentLoaded", function() {
	init();
});
