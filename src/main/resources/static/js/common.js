var CommonClassEditObj = {
	addClass: function(element, classString) {
		element.className = element
			.className
			.split(' ')
			.filter(function(name) { return name !== classString; })
			.concat(classString)
			.join(' ');
	},
	removeClass: function(element, classString) {
		element.className = element
			.className
			.split(' ')
			.filter(function(name) { return name !== classString; })
			.join(' ');
	}
}

var CommonEventObj = {
	setEventListeners: function() {
		document.querySelector(".gototop").addEventListener("click", () => {
			window.scrollTo({ top: 0, left: 0, behavior: 'smooth' });
		});
	}
}

function initCommonConfig() {
	CommonEventObj.setEventListeners();
}

document.addEventListener("DOMContentLoaded", () => {
	initCommonConfig();
});