function mukouSeigyo() {

	document.oncontextmenu = noncontextmenu;
	document.onkeydown = keydownevent;
}

function noncontextmenu() {
	if (window.event.stopPropagation) {
		window.event.stopPropagation = true;
		window.event.preventDefault();
	} else {
		window.event.cancelBubble = true;
		window.event.returnValue = false;
	}
}

function keydownevent() {
	if (window.event.altKey && (window.event.keyCode == 37 || window.event.keyCode == 39)) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 8 || window.event.keyCode == 0) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 112) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 113) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 114) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 115) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 116) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 117) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 118) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 119) {
		window.event.keyCode = 0;
		controlEvent();
	}
	if (window.event.keyCode == 120) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 121) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 122) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.keyCode == 123) {
		window.event.keyCode = 0;
		controlEvent();
	}

	if (window.event.ctrlKey) {
		if (window.event.keyCode == 70 || window.event.keyCode == 79 || window.event.keyCode == 80) {
			window.event.keyCode = 79;
		}

		controlEvent();
	}

	if (window.event.shiftKey && window.event.keyCode == 121) {
		controlEvent();
	}
}

function controlEvent() {
	if (window.event.preventDefault) {
		window.event.preventDefault();
	} else {
		window.event.returnValue = false;
	}
}