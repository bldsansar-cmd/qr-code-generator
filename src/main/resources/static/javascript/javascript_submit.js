//submit
//言語変更処理 
function changeLanguage(language) {
	form.locale.value = language;
	form.operation_type.value = 'qr';
	setSmartPc();
	form.action = '/qrazy/language';
	form.submit();
}

function changeUserMenu(userMenu) {
	if (userMenu == 'mypage') {
		goToUser();
	} else {
		logout();
	}
	return;
}

function goToUser() {
	setSmartPc();
	form.action = '/qrazy/user';
	form.submit();
}

function login() {

	form.operation_type.value = 'login';
	form.action = '/qrazy/login';
	form.submit();
}

function logout() {
	form.action = '/qrazy/logout';
	form.submit();
}

function userUpdate() {
	form.profile_img.value = form.plug_img.src;
	form.action = '/qrazy/userUpdate';
	form.submit();
}

//オファーページ遷移処理 
function goToOffer() {

	setSmartPc();
	form.action = '/qrazy/offer';
	form.submit();
}

//製品ページ遷移処理 
function goToProduct(productId) {
	form.product.value = productId;
	setSmartPc();
	form.action = '/qrazy/product';
	form.submit();
}

//ソリューションページ遷移処理 
function goToSolution(solutionId) {
	form.solution.value = solutionId;
	setSmartPc();
	form.action = '/qrazy/solution';
	form.submit();
}

//作成処理 
function create() {
	setSmartPc();
	form.orgBase64Data.value = '';
	form.operation_type.value = 'qr';

	//	form.qr_generate_type.value = switchStaticDynamic.checked ? 'dynamic' : 'static'; 
	var srcValue = document.getElementById('preview').src;
	if (srcValue.includes("data:image")) {
		form.plug_img.value = srcValue;
		form.logo_needed.value = "1";
	} else {
		form.plug_img.value = "";
		form.logo_needed.value = "0";
	}

	form.img_size.value = DEFAULT_IMG_SIZE;
	form.img_type.value = DEFAULT_IMG_TYPE;
	form.action = '/qrazy/create';
	form.submit();
}

//メニュー変更処理 
function changeQrType(selectedObj) {
	form.qr_type.value = selectedObj.classList[selectedObj.classList.length - 1];
	window.scroll({
		top: 0,
		behavior: 'smooth'
	});

	waitAndDo(100, '/qrazy/change');
}

//リセット処理 
function reset() {
	window.scroll({
		top: 0,
		behavior: 'smooth'
	});

	waitAndDo(100, '/qrazy/reset');
}

//指定時間経過後の処理 
function waitAndDo(waitTime, actionName) {

	if (gTimer) {
		clearTimeout(gTimer);
	}

	gTimer = setTimeout(function () {
		form.action = actionName;
		form.submit();
	}, waitTime);
}

//ダウンロード処理 
function download() {

	form.orgBase64Data.value = '';
	form.action = '/qrazy/download'
	form.submit();
}

//ダウンロード処理 
function save() {
	form.orgBase64Data.value = '';
	form.action = '/qrazy/save'
	form.submit();
}

//メイン画面に遷移処理 
function goHome() {
	form.action = '/qrazy/back'
	form.submit();
}

function setSmartPc() {
	if (window.innerWidth <= smartPhoneWidth) {
		form.smartFlg.value = SMART_PHONE;
	} else {
		form.smartFlg.value = '';
	}
}

function dynamicQrChange(uniqNumber) {
	console.log(uniqNumber);
	return;
}