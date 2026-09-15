//画面ロード処理
function createIndex() {
	// mukouSeigyo(); 
	//画面制御 
	gamenSeigyo();

	//イベント登録 
	eventTouroku();

	return;
}

function gamenSeigyo() {

	//ステップナビゲーション制御 
	stepNavSeigyo();

	//メニューボタン制御 
	menuBtnSeigyo();

	//入出力表示枠リセット 
	resetOutline();

	//入力エリア制御 
	inputSectionSeigyo();

	//出力エリア制御 
	outputSectionSeigyo();

	loginSectionSeigyo();

	//ダウンロードセクションへ移動 
	doScroll();
}

function loginSectionSeigyo() {

	if (operationType == 'login') {
		goToLogin();
	}
}

//丸ボタン制御処理 
function controlToggleBtn(targetObj) {

	changeToggleBtn(targetObj);

	//拡張ボタン表示値変更 
	doExpand(targetObj);

}

//丸ボタン押下による指定セクション拡張処理 
function doExpand(targetObj) {

	if (targetObj.classList.contains('btn_light_learn')) {
		arrangeInlineSection(targetObj, ".btn_light_learn", ".section_detail_learn");
	} else if (targetObj.classList.contains('btn_light_solution')) {
		arrangeInlineSection(targetObj, ".btn_light_solution", ".section_detail_solution");
	} else {
		return;
	}
}

function arrangeInlineSection(targetBtn, btnClsName, sectionClsName) {

	var resultList = document.querySelectorAll(sectionClsName);
	var index = getTargetIndex(targetBtn, btnClsName);

	if (index != null) {
		if (targetBtn.classList.contains('expand')) {
			elementOpenClose(resultList[index], openTypeFlex);
		} else {
			elementOpenClose(resultList[index], openTypeNone);
		}
	}
}

function getTargetIndex(targetBtn, btnClsName) {

	var result = null;
	var resultList = document.querySelectorAll(btnClsName);

	for (var i = 0; i < resultList.length; i++) {
		if (targetBtn === resultList[i]) {
			result = i;
			break;
		}
	}

	return result;
}

//画像ファイル取り込み処理 
function imgPreview(hoge) {

	fileData = new FileReader();
	elementOpenClose(imgPreviewArea, openTypeBlock);

	fileData.onload = (function () {
		document.getElementById('preview').src = fileData.result;
		imageUploaded = true;
		asyncSendCreate(true);
	});

	fileData.readAsDataURL(hoge.files[0]);
	logoSizeStyle.style.display = 'block';
}

function customizeStyleChanged(targetObj, type, value) {

	for (var i = 0; i < customizeNavBoxList.length; i++) {
		customizeNavBoxList[i].classList.remove('selected_nav_around');
	}

	targetObj.classList.add('selected_nav_around');

	switch (type) {
		case 'pixel':
			form.customize_pixel.value = value;
			break;
		case 'finder':
			form.customize_finder.value = value;
			break;
		case 'frame':
			form.customize_frame.value = value;
			break;
		case 'template':
			form.customize_template.value = value;
			break;
		default:
	}

	asyncSendCreate(true);
}

function asyncSendCreate(eventTriggered) {

	if (eventTriggered == false) {
		return;
	}

	if (form.orgBase64Data.value != "") {
		// XMLHttpRequestオブジェクトのインスタンスを生成 
		let request = new XMLHttpRequest();

		// レスポンスの形式を指定 
		request.responseType = "json";

		// リクエストメソッドとリクエスト先を設定 
		request.open("POST", "/qrazy/createByAsync");

		form.orgBase64Data.value = '';
		form.img_size.value = DEFAULT_IMG_SIZE;
		form.img_type.value = DEFAULT_IMG_TYPE;

		if (document.getElementById('preview').src.includes("data:image")) {
			form.logo_needed.value = "1";
		} else {
			form.logo_needed.value = "0";
		}

		let formData = new FormData(form);
		request.send(formData);

		// 通信を監視するイベントハンドラを設定 
		request.onreadystatechange = function () {

			// 通信完了時 
			if (request.readyState === 4) {
				if (request.status === 200 && request.response != null) {
					// 正常レスポンス 
					form.qr_path.value = request.response.qr_path;
					form.file_name.value = request.response.file_name;
					form.orgBase64Data.value = request.response.orgBase64Data;
					form.zipBase64Data.value = request.response.zipBase64Data;

					if (qrCodeImg != null) {
						if (request.response.zipBase64Data == '') {
							qrCodeImg.src = 'data:image/png;base64,' + request.response.orgBase64Data;
						} else {
							qrCodeImg.src = request.response.zipBase64Data;
						}
					}

					if (request.response.base64DataList.length > 1) {
						var bulkQrImgList = request.response.base64DataList;
						var popupQrcodeSingleList = document.querySelectorAll('.popup_output_qrcode_single');

						for (var i = 0; i < bulkQrImgList.length; i++) {
							var imgObj = popupQrcodeSingleList[i].lastElementChild;
							imgObj.src = bulkQrImgList[i];
						}
					}
				} else {
					console.log("通信エラー");
				}
			}
		}
	}
}

function asyncSendSave() {

	if (form.orgBase64Data.value != "") {

		// XMLHttpRequestオブジェクトのインスタンスを生成 
		let request = new XMLHttpRequest();

		// レスポンスの形式を指定 
		request.responseType = "json";

		// リクエストメソッドとリクエスト先を設定 
		request.open("POST", "/qrazy/saveByAsync");
		let formData = new FormData(document.forms[0]);
		request.send(formData);

		// 通信を監視するイベントハンドラを設定 
		request.onreadystatechange = function () {

			// 通信完了時
			if (request.readyState === 4) {

				if (request.status === 200 && request.response != null) {
					// 正常レスポンス 
					alert("save completed");
				} else {
					console.log("通信エラー");
				}
			}
		}
	}
}

//画像ファイル取り込み処理 
function filePreview(fileInfo) {

	fileData = new FileReader();
	fileData.onload = (function () {

		document.getElementById('previewUploadFileName').innerText = fileInfo.files[0].name;
		document.getElementById('previewUploadFile').src = fileData.result;
		form.file_name.value = fileInfo.files[0].name;
	});

	fileData.readAsDataURL(fileInfo.files[0]);
}

//画面リセット 
function resetDisplay() {

	var inputValueName;
	var inputValue;
	inputValueName = 'input_value_' + form.qr_type.value;

	if (form.qr_type.value == '') {
		return;
	} else if (form.qr_type.value == 'wifi') {
		if (document.getElementsByName(inputValueName + '_id')[0].value == "" || document.getElementsByName(inputValueName + '_pass')[0].value == "") {
			inputValue = "";
		}
	} else {
		inputValue = document.getElementsByName(inputValueName)[0].value;
	}

	if (inputValue == "") {
		resetInput();
	}

	arrangeStaticDynamicSwitcher();
	arrangeLogoNeededSwitcher(false);
	arrangeLogoSizeSwitcher(false);
}

function setPopupEvent() {

	popupContent = document.querySelector(".section_outline_customize");
	popupContent.addEventListener("mousedown", function (event) {
		elemOffsetX = event.offsetX;
		elemOffsetY = event.offsetY;
		document.addEventListener("mousemove", dragWindow);
	});

	document.addEventListener("mouseup", function () {
		document.removeEventListener("mousemove", dragWindow);
	});
}

function dragWindow(event) {
	popupContent.style.left = event.clientX - elemOffsetX;
	popupContent.style.top = event.clientY - elemOffsetY;
}

function resetStepNav() {
	elementDisableEnable(stepNavSelect, true, classNameCompleted);
	elementDisableEnable(stepNavInput, true, classNameCompleted);
	elementDisableEnable(stepNavGenerate, true, classNameCompleted);
	elementDisableEnable(stepNavLineInput, true, classNameCompleted);
	elementDisableEnable(stepNavLineGenerate, true, classNameCompleted);
}

function stepNavSeigyo() {

	if (form.orgBase64Data.value != "") {
		elementDisableEnable(stepNavSelect, false, classNameCompleted);
		elementDisableEnable(stepNavInput, false, classNameCompleted);
		elementDisableEnable(stepNavGenerate, false, classNameCompleted);
		elementDisableEnable(stepNavLineInput, false, classNameCompleted);
		elementDisableEnable(stepNavLineGenerate, false, classNameCompleted);
	} else {
		resetStepNav();

		if (form.qr_type.value != "") {
			elementDisableEnable(stepNavSelect, false, classNameCompleted);
		}
	}
}

//入力項目制御 
function inputSectionSeigyo() {

	var selectedQrType;
	resetDisplay();
	inputOutlineSeigyo();
	selectedQrType = form.qr_type.value;

	for (var i = 0; i < inputArea.length; i++) {
		if (form.qr_type.value == "") {
			elementOpenClose(inputArea[i], openTypeNone);
		} else {
			if (inputArea[i].classList.contains('input_menu_common')) {
				elementOpenClose(inputArea[i], openTypeBlock);
			} else if (!inputArea[i].classList.contains('input_menu_' + selectedQrType)) {
				elementOpenClose(inputArea[i], openTypeNone);
			} else {
				if (selectedQrType == "wifi") {
					elementOpenClose(inputArea[i], openTypeBlock);
				} else {
					elementOpenClose(inputArea[i], openTypeBlock);
				}
			}
		}
	}

	qrGenerateType();
}

function openPopup(divName) {
	elementOpenClose(emptyLayer, openTypeBlock);
	elementOpenClose(document.querySelector("." + divName), openTypeFlex);

	if (navBtn.classList.contains("close")) {
		navBtn.classList.toggle("close");
		elementOpenClose(emptyLayer, openTypeNone);
		openMenuList(false, navMenu);
	}

	window.scroll({
		top: 0,
		behavior: 'smooth'
	});
}

function toggleMenuState(targetObj) {

	if (targetObj.classList.contains("close")) {
		targetObj.classList.toggle("close");
	}
}

function closeDiv(pushBtn) {

	if (pushBtn === navBtn) {
		toggleMenuState(navLang);
		closePopup('nav_lang');
		toggleMenuState(navUser);
		closePopup('nav_user');
	} else if (pushBtn === navLang) {
		toggleMenuState(navUser);
		closePopup('nav_user');
		toggleMenuState(navBtn);
		closePopup('nav_menu');
	} else if (pushBtn === navUser) {
		toggleMenuState(navLang);
		closePopup('nav_lang');
		toggleMenuState(navBtn);
		closePopup('nav_menu');
	} else {

		toggleMenuState(navLang);
		closePopup('nav_lang');
		toggleMenuState(navUser);
		closePopup('nav_user');
		toggleMenuState(navBtn);
		closePopup('nav_menu');
	}
}

function closePopup(divName) {

	elementOpenClose(emptyLayer, openTypeNone);
	elementOpenClose(document.querySelector("." + divName), openTypeNone);

	if (divName == 'popup_login' && operationType == 'login') {
		inputValueLoginId.classList.remove('border_error');
		inputValueLoginId.value = '';
		inputValueLoginPass.classList.remove('border_error');
		inputValueLoginPass.value = '';

		for (var i = 0; i < textErrorLogin.length; i++) {
			textErrorLogin[i].innerText = '';
		}
	}
}

function descriptionSeigyo() {

	for (var i = 0; i < document.querySelectorAll(".dynamic_only").length; i++) {
		elementOpenClose(document.querySelectorAll(".dynamic_only")[i], openTypeNone);
	}

	for (let i = 0; i < dynamicOnlyQrTypeList.length; i++) {

		if (dynamicOnlyQrTypeList[i] == form.qr_type.value) {
			for (var j = 0; j < document.querySelectorAll(".dynamic_only").length; j++) {
				elementOpenClose(document.querySelectorAll(".dynamic_only")[j], openTypeBlock);
			}

			break;
		}
	}
}

function qrGenerateType() {

	if (generateTypesOption != null) {
		descriptionSeigyo();
		generateTypesOption.style.display = 'none';

		for (let i = 0; i < dynamicStaticOptionQrTypeList.length; i++) {
			if (dynamicStaticOptionQrTypeList[i].value == form.qr_type.value) {
				generateTypesOption.style.display = 'block';
				break;
			}
		}
	}
}

//入力項目制御 
function outputSectionSeigyo() {

	outputOutlineSeigyo();
	document.getElementById('preview').src = form.plug_img.value;
}

function outputOutlineSeigyo() {
	elementOpenClose(generatorBtns, openTypeFlex);
	if (form.orgBase64Data.value != "") {
		elementOpenClose(sectionDownload, openTypeBlock);
	} else {

		elementOpenClose(sectionDownload, openTypeNone);
		if (textErrors[0] != undefined) {
			elementOpenClose(generatorBtns, openTypeFlex);
		}
	}
}

function goToLogin() {

	closeDiv(navBtn);
	closeDiv(navLang);
	closeDiv(navUser);
	closePopup('popup_description_static_dynamic');
	openPopup('popup_login');
}

//ナビゲーション変更処理 
function arrangeNavMenu() {

	closeDiv(navBtn);
	closePopup('popup_login');
	closePopup('popup_description_static_dynamic');
	navBtn.classList.toggle("close");

	if (navBtn.classList.contains("close")) {
		elementOpenClose(emptyLayer, openTypeBlock);
		openMenuList(true, navMenu);
	} else {
		elementOpenClose(emptyLayer, openTypeNone);
		openMenuList(false, navMenu);
	}
}

//ナビゲーション変更処理 
function arrangeLangMenu() {

	closeDiv(navLang);
	closePopup('popup_login');
	closePopup('popup_description_static_dynamic');

	navLang.classList.toggle("close");

	if (navLang.classList.contains("close")) {
		elementOpenClose(emptyLayer, openTypeBlock);
		openMenuList(true, navLang);
	} else {
		elementOpenClose(emptyLayer, openTypeNone);
		openMenuList(false, navLang);
	}
}

//ナビゲーション変更処理 
function arrangeUserMenu() {

	closeDiv(navUser);
	closePopup('popup_login');
	closePopup('popup_description_static_dynamic');
	navUser.classList.toggle("close");

	if (navUser.classList.contains("close")) {
		elementOpenClose(emptyLayer, openTypeBlock);
		openMenuList(true, navUser);
	} else {
		elementOpenClose(emptyLayer, openTypeNone);
		openMenuList(false, navUser);
	}
}

function openMenuList(openFlg, targetMenu) {

	if (openFlg) {
		elementOpenClose(targetMenu, openTypeBlock);
	} else {
		elementOpenClose(targetMenu, openTypeNone);
	}
}

//Util 
//画面要素選択状態制御 
function elementDisableEnable(targetElm, disable, className) {

	if (targetElm != null) {
		targetElm.disabled = disable;
		elementToSelect(targetElm, !disable, className)
	}
}

function elementToSelect(targetElm, toSelect, className) {

	if (toSelect) {
		targetElm.classList.add(className);
	} else {
		targetElm.classList.remove(className);
	}
}

function elementOpenClose(targetElm, openType) {

	if (targetElm != null && targetElm != undefined) {
		targetElm.style.display = openType;
	}
}

//scroll 
function doScroll() {

	scrollUp();
	//QRコード作成済みの場合、ダウンロードセクションへ移動 
	if (form.orgBase64Data.value != "") {
		scrollToElement("section_customize");
	}
}

function scrollUp() {

	window.scroll({
		top: 0,
		behavior: 'smooth'
	});
}

function scrollFromMenu(elemId) {
	arrangeNavMenu();
	scrollToElement(elemId);
}

//指定のセクションへ移動（ヘッダの高さ分調整） 
function scrollToElement(elemId) {

	var headerElement = document.querySelector('.header');
	var style = window.getComputedStyle(headerElement);
	var customProperty = style.getPropertyValue('--header_height');
	var scrollHeight = Number(customProperty.replace(/px$/, ''));

	if (window.innerWidth > smartPhoneWidth) {
		scrollHeight = scrollHeight + 10;
	}

	var scrollDiv;
	if (document.getElementById('section_' + elemId) != null) {
		scrollDiv = document.getElementById('section_' + elemId).offsetTop - scrollHeight;
	} else {
		if (document.getElementById(elemId) != null) {
			scrollDiv = document.getElementById(elemId).offsetTop - scrollHeight;
		}
	}

	window.scrollTo({
		top: scrollDiv,
		behavior: 'smooth'
	});
}

//画面上部へ移動処理 
function scrollEvent() {

	if (window.pageYOffset > 300) {
		scrollBtn.style.opacity = '1';
	} else if (window.pageYOffset < 300) {
		scrollBtn.style.opacity = '0';
	}
}

//menu button 
function menuBtnSeigyo() {
	controlResize();
}

function getDispSizeType(nowSmartPhone) {

	var result = 0;
	if (nowSmartPhone && !smartPhone) {
		result = SMART_PHONE;
	}

	if (!nowSmartPhone && smartPhone) {
		result = PC;
	}

	return result;
}

function resizeDisp() {
	controlResize();
}

function controlResize() {

	if (styleToggleBtn != null) {
		if (window.innerWidth <= tabletWidth) {
			// styleToggleBtn.style.display = 'block'; 
			if (styleToggleBtn.classList.contains('expand')) {
				styleToggleBtn.innerHTML = reduceMark;
				// sectionDataAppendStyle.style.display = 'block'; 
			} else {
				styleToggleBtn.innerHTML = expandMark;
				// sectionDataAppendStyle.style.display = 'none'; 
			}
		} else {
			// styleToggleBtn.style.display = 'none'; 
			// sectionDataAppendStyle.style.display = 'block'; 
		}
	}
}

//function arrangedStyleSection(changedTo) { 
// 
// if(changedTo == SMART_PHONE) { 
// sectionDataAppendStyle.style.display = 'none'; 
// sectionCustomizeNavBtn.appendChild(sectionDataAppendStyle); 
// sectionOutlineCustomize.appendChild(sectionCustomizeNavBtn); 
//  
// if(styleToggleBtn.classList.contains('expand')) { 
// styleToggleBtn.classList.remove('expand'); 
// } 
// styleToggleBtn.innerHTML = expandMark; 
// 
// } else { 
// sectionDataAppendStyle.style.display = 'block'; 
// sectionOutlineCustomize.appendChild(sectionCustomizeNavBtn); 
// sectionOutlineCustomize.appendChild(sectionDataAppendStyle);  
// } 
//
//} 

function changeToggleBtn(targetObj) {
	targetObj.classList.toggle("expand");
	if (targetObj.classList.contains("expand")) {
		targetObj.innerHTML = reduceMark;
	} else {
		targetObj.innerHTML = expandMark;
	}
}

function controlMenuToggleBtn(targetObj) {

	changeToggleBtn(targetObj);
	for (var i = 0; i < qrMenuBtn.length; i++) {
		if (targetObj.classList.contains("expand")) {
			if (window.innerWidth <= smartPhoneWidth) {
				if (qrMenuBtn[i].style.display == 'none') {
					qrMenuBtn[i].style.display = '';
				}
			}
		} else {
			if (window.innerWidth <= smartPhoneWidth) {
				if (!qrMenuBtn[i].classList.contains('selected')) {
					qrMenuBtn[i].style.display = 'none';
				}
			}
		}
	}
}

function controlStyleToggleBtn(targetObj) {

	changeToggleBtn(targetObj);
	sectionCustomizeNavBtn.appendChild(sectionDataAppendStyle);

	if (targetObj.classList.contains("expand")) {
		sectionDataAppendStyle.style.display = 'block';
		// scrollToElement("section_download"); 
	} else {
		sectionDataAppendStyle.style.display = 'none';
	}
}

function controlProfileToggleBtn(targetObj) {

	changeToggleBtn(targetObj);
	sectionProfileNav.appendChild(sectionDataAppendProfile);

	if (targetObj.classList.contains("expand")) {
		sectionDataAppendProfile.style.display = 'block';
	} else {
		sectionDataAppendProfile.style.display = 'none';
	}
}

function changeQrTypePc(selectedObj, targetBtns) {

	for (var i = 0; i < targetBtns.length; i++) {
		targetBtns[i].classList.remove('selected');
	}

	selectedObj.classList.add('selected');
}

//color picker 
function colorPickerSakusei() {
	colorPickerSakuseiNav("bg");
	colorPickerSakuseiNav("pixel");
	colorPickerSakuseiNav("finder");
	colorPickerSakuseiDetail();
}

function colorPickerSakuseiDetail() {
	var colorPickerDetail = document.querySelector(".color_picker_detail_bg");
	colorPickerSakuseiDetailCommon(colorPickerDetail, bgColorList, "bg");
	colorPickerDetail = document.querySelector(".color_picker_detail_pixel");
	colorPickerSakuseiDetailCommon(colorPickerDetail, pixelColorList, "pixel");
	colorPickerDetail = document.querySelector(".color_picker_detail_finder");
	colorPickerSakuseiDetailCommon(colorPickerDetail, finderColorList, "finder");
}

function colorPickerSakuseiDetailCommon(targetObj, colorList, type) {

	if (targetObj.children.length > 0) {
		return;
	}

	colorList.forEach(color => {
		var elem = document.createElement("div");
		elem.classList.add("color_picker_detail_single");
		elem.style.backgroundColor = color.value;
		elem.setAttribute("onclick", "selectColor('" + color.value + "', '" + type + "')");
		targetObj.appendChild(elem);
	});

	targetObj.classList.add("none");
}

function selectColor(color, type) {
	var colorPickerInput;
	var colorPickerDetail;
	if (type == "bg") {
		colorPickerInput = document.getElementById("bg_color");
		colorPickerDetail = colorPickerDetailBg;
	} else if (type == "pixel") {
		colorPickerInput = document.getElementById("pixel_color");
		colorPickerDetail = colorPickerDetailPixel;
	} else if (type == "finder") {
		colorPickerInput = document.getElementById("finder_color");
		colorPickerDetail = colorPickerDetailFinder;
	}

	colorPickerInput.value = colorToHex(color);
	adjustColorPicker(colorPickerDetail, color);
	colorPickerSakuseiNav(type);
	asyncSendCreate(true);
}

function colorPickerSakuseiNav(type) {

	if (type == "bg") {
		colorPickerInput = document.getElementById("bg_color");
		colorPickerNav = colorPickerSimpleNavBg;
	} else if (type == "pixel") {
		colorPickerInput = document.getElementById("pixel_color");
		colorPickerNav = colorPickerSimpleNavPixel;
	} else if (type == "finder") {
		colorPickerInput = document.getElementById("finder_color");
		colorPickerNav = colorPickerSimpleNavFinder;
	}

	asyncSendCreate(true);
}

function adjustColorPicker(targetObj, color) {
	adjustColorPickerCommon(targetObj, color);
}

function adjustColorPickerCommon(targetObj, color) {

	if (targetObj.classList.contains("flexbox") || color == "") {
		targetObj.classList.remove("flexbox");
		targetObj.classList.add("none");
	} else {
		targetObj.classList.add("flexbox");
		targetObj.classList.remove("none");
	}
}

function colorToHex(color) {

	// Convert any CSS color to a hex representation 

	// Examples: 

	// colorToHex('red') # '#ff0000' 

	// colorToHex('rgb(255, 0, 0)') # '#ff0000' 

	var rgba, hex;
	rgba = colorToRGBA(color);
	hex = [0, 1, 2].map(
		function (idx) {
			return byteToHex(rgba[idx]);
		}

	).join('');

	return "#" + hex.toUpperCase();
}

function colorToRGBA(color) {
	// Returns the color as an array of [r, g, b, a] -- all range from 0 - 255 
	// color must be a valid canvas fillStyle. This will cover most anything 
	// you'd want to use. 
	// Examples: 
	// colorToRGBA('red') # [255, 0, 0, 255] 
	// colorToRGBA('#f00') # [255, 0, 0, 255] 

	var cvs, ctx;
	cvs = document.createElement('canvas');
	cvs.height = 1;
	cvs.width = 1;
	ctx = cvs.getContext('2d');
	ctx.fillStyle = color;
	ctx.fillRect(0, 0, 1, 1);

	return ctx.getImageData(0, 0, 1, 1).data;
}

function byteToHex(num) {
	// Turns a number (0-255) into a 2-character hex number (00-ff) 
	return ('0' + num.toString(16)).slice(-2);
}

function inputColorChanged(inputObj, type) {
	asyncSendCreate(true);
}

function inputColorStarted(type) {
	var colorPickerDetail;
	if (type == "bg") {
		colorPickerDetail = colorPickerDetailBg;
	} else if (type == "pixel") {
		colorPickerDetail = colorPickerDetailPixel;
	} else if (type == "finder") {
		colorPickerDetail = colorPickerDetailFinder;
	}

	adjustColorPicker(colorPickerDetail, "");
}

function resetOutline() {
	elementOpenClose(sectionOutlineInput, openTypeNone);
	elementOpenClose(sectionDownload, openTypeNone);
	// elementOpenClose(generatorBtns, openTypeNone); 
}

function arrangeCreatedQrSection(openFlg) {

	if (openFlg) {
		sectionCustomize.classList.remove('noborder');
		sectionDownload.classList.remove('noborder');
		elementOpenClose(sectionCustomize, openTypeBlock);
		elementOpenClose(sectionDownload, openTypeBlock);
		const gridContainer = document.querySelector('.container_generator');
		// gridContainer.style.gridGap = 'var(--gap_1_5)'; 
	} else {
		sectionCustomize.classList.add('noborder');
		sectionDownload.classList.add('noborder');
		elementOpenClose(sectionCustomize, openTypeNone);
		elementOpenClose(sectionDownload, openTypeNone);
	}
}

function inputOutlineSeigyo() {
	arrangeCreatedQrSection(false);
	var inputCompleted = isInputElemFilled();

	if (form.qr_type.value != "") {
		elementOpenClose(sectionOutlineInput, openTypeBlock);
	}

	if (form.orgBase64Data.value != "") {
		arrangeCreatedQrSection(true);
		elementOpenClose(generatorBtns, openTypeFlex);
	} else {
		if (inputCompleted) {
			elementOpenClose(generatorBtns, openTypeFlex);
		}
	}

	var selectedCustomizeNavObj = document.querySelector('.customize_nav_' + selectedCustomizeNav);
	arrangeCustomizeNavInput(selectedCustomizeNavObj);
}

function isInputElemFilled() {
	var result = false;

	for (i = 0; i < inputElmDatas.length; i++) {
		if (!inputElmDatas[i].classList.contains('popup_elm') && inputElmDatas[i].value != '') {
			result = true;
			break;
		}
	}

	return result;
}

//入力項目リセット処理 
function resetInput() {

	document.getElementById('preview').src = '';

	for (var i = 0; i < inputArea.length; i++) {
		var children = inputArea[i].children;
		for (var j = 0; j < children.length; j++) {

			if (children[j].value != undefined) {
				children[j].value = "";
				children[j].classList.remove('border_error');
			}

			if (children[j].classList.contains('text_error')) {
				children[j].innerText = '';
			}

			if (children[j].classList.contains('input_menu_wifi_info')) {
				for (k = 0; k < children[j].children.length; k++) {
					if (children[j].children[k].tagName == 'INPUT') {
						children[j].children[k].value = "";
						children[j].children[k].classList.remove('border_error');
					} else {
						if (children[j].children[k].classList.contains('text_error')) {
							children[j].children[k].innerText = '';
						}
					}
				}
			}
		}
	}

	resetOutline();
}

function resetCustomizeSection() {
	var select = document.getElementById("img_type");
	select.options[1].selected = true;
	select = document.getElementById("img_size");
	select.options[1].selected = true;
	form.customize_pixel.value = 1;
	form.customize_finder.value = 1;
	form.customize_frame.value = 1;
	form.customize_template.value = 1;
	switchLogo.checked = false;
	arrangeLogoNeededSwitcher(false);
	arrangeLogoSizeSwitcher(false);
	adjustColorPicker(colorPickerDetailBg, "WHITE");
	adjustColorPicker(colorPickerDetailFinder, "BLACK");
	adjustColorPicker(colorPickerDetailPixel, "BLACK");
}

//入力内容変更処理 
function inputValueChanged(targetObj) {

	resetErrorText();
	if (targetObj.classList.contains('popup_elm') || targetObj.name == 'input_value_qr_name') {
		return;
	}

	var inputCompleted = false;
	if (isInputCompleted(targetObj)) {
		inputCompleted = true;
	} else {
		inputCompleted = false;
	}

	if (inputCompleted) {
		elementOpenClose(generatorBtns, openTypeFlex);

		if (form.orgBase64Data.value != "") {
			arrangeCreatedQrSection(true);
		}

		elementDisableEnable(stepNavInput, false, classNameCompleted);
		elementDisableEnable(stepNavLineInput, false, classNameCompleted);
	} else {
		// elementOpenClose(generatorBtns, openTypeNone); 
		elementOpenClose(sectionDownload, openTypeNone);
		arrangeCreatedQrSection(false);
	}
}

function resetErrorText() {

	for (var j = 0; j < textErrors.length; j++) {
		textErrors[j].innerText = '';
		textErrors[j].classList.remove('border_error');
	}
}

function isInputCompleted(targetObj) {
	var result = false;
	var qrType = '';
	qrType = form.qr_type.value;

	if ((qrType == 'wifi' && form.input_value_wifi_id.value != "" && form.input_value_wifi_pass.value != "") ||
		(qrType != 'wifi' && targetObj.value != "")) {
		result = true;
	}

	return result;
}

function resetOutput() {
	form.orgBase64Data.value = "";
	form.file_name.value = "";
	form.orgBase64Data.value = "";

	if (qrCodeImg != null) {
		qrCodeImg.src = '';
	}

	stepNavSeigyo();
}

function arrangeStaticDynamicSwitcher() {

	if (switchStaticDynamicDescription != null) {
		switchStaticDynamicDescription.textContent = switchStaticDynamic.checked ? dynamicSwitchText : staticSwitchText;
	}
}

function arrangeImgTypeSwitcher() {

	switchImgTypeDescription.textContent = switchImgType.checked ? "svg" : "png";
	form.img_type.value = switchImgType.checked ? "svg" : "png";
}

function arrangeLogoSizeSwitcher(eventTriggered) {

	switchLogoSizeDescription.textContent = switchLogoSize.checked ? textBig : textSmall;
	form.logo_size.value = switchLogoSize.checked ? "1" : "0";
	asyncSendCreate(eventTriggered);

}

function arrangeLogoNeededSwitcher(eventTriggered) {

	switchLogoNeededDescription.textContent = switchLogo.checked ? withLogoText : withoutLogoText;
	if (switchLogo.checked) {
		switchLogoNeededDescription.textContent = withLogoText;
		elementOpenClose(fileSelect, openTypeBlock);
	} else {
		switchLogoNeededDescription.textContent = withoutLogoText;
		elementOpenClose(fileSelect, openTypeNone);
		logoSizeStyle.style.display = 'none';
	}

	if (switchLogo.checked && document.getElementById('preview').src != '') {
		elementOpenClose(imgPreviewArea, openTypeBlock);
	} else {

		if (document.getElementById('preview').src.includes("data:image")) {
			eventTriggered = true;
		} else {
			eventTriggered = false;
		}

		elementOpenClose(imgPreviewArea, openTypeNone);
		document.getElementById('preview').src = '';
		imageUploaded = false;
		fileElem.value = '';
	}

	asyncSendCreate(eventTriggered);
}

function arrangeCustomizeNavInput(targetNav) {

	for (var i = 0; i < customizeNavs.length; i++) {
		var targetInput;
		if (targetNav.classList.contains(customizeNavs[i])) {

			form.selectedCustomizeNav.value = customizeNavs[i].split('_')[customizeNavs[i].split('_').length - 1];
			targetNav.classList.add('selected_nav');
			targetInput = document.querySelector('.' + customizeNavs[i] + '_input');

			if (targetInput != null) {
				targetInput.style.display = 'flex';
			}
		} else {
			var tmpNav = document.querySelector('.' + customizeNavs[i]);
			var tmpInput = document.querySelector('.' + customizeNavs[i] + '_input');

			if (tmpNav != null) {
				tmpNav.classList.remove('selected_nav');
			}

			if (tmpInput != null) {
				tmpInput.style.display = 'none';
			}
		}
	}
}

function showQrCodes() {
	openPopup('popup_bulk_qr_codes');
}