var smartMenuBtnClicked = false;

//各種イベント登録
function eventTouroku() {

	//ログインボタン押下処理 
	if (loginBtn != undefined && loginBtn != null) {
		loginBtn.addEventListener('click', () => {
			login();
		});
	}

	//作成ボタン押下処理 
	if (createBtn != undefined && createBtn != null) {
		createBtn.addEventListener('click', () => {
			stepNavSeigyo();
			create();
		});
	}

	//リセットボタン押下処理 
	if (resetBtn != undefined && resetBtn != null) {
		resetBtn.addEventListener('click', () => {
			reset();
		});
	}

	//ダウンロードボタン押下処理 
	if (downloadBtn != undefined && downloadBtn != null) {
		downloadBtn.addEventListener('click', () => {
			download();
		});
	}

	//トラックボタン押下処理 
	if (trackBtn != undefined && trackBtn != null) {
		trackBtn.addEventListener('click', () => {
			asyncSendSave();
		});
	}

	//Showボタン押下処理 
	if (showBtn != undefined && showBtn != null) {
		showBtn.addEventListener('click', () => {
			showQrCodes();
		});
	}

	//拡張ボタン押下処理 
	if (lightBtnExpand != undefined && lightBtnExpand != null) {
		for (var i = 0; i < lightBtnExpand.length; i++) {
			lightBtnExpand[i].addEventListener('click', (e) => {
				controlToggleBtn(e.currentTarget);
			});
		}
	}

	if (scrollBtn != undefined && scrollBtn != null) {
		scrollBtn.addEventListener('click', () => {
			scrollUp();
		});
	}

	//スクロールボタン押下処理 
	window.addEventListener('scroll', () => {
		scrollEvent()
	});

	if (colorPickerSimpleNavBg != undefined && colorPickerSimpleNavBg != null) {
		colorPickerSimpleNavBg.addEventListener("click", () => {
			adjustColorPicker(colorPickerDetailBg, "WHITE");
		});
	}

	if (colorPickerSimpleNavPixel != undefined && colorPickerSimpleNavPixel != null) {
		colorPickerSimpleNavPixel.addEventListener("click", () => {
			adjustColorPicker(colorPickerDetailPixel, "BLACK");
		});
	}

	if (colorPickerSimpleNavFinder != undefined && colorPickerSimpleNavFinder != null) {
		colorPickerSimpleNavFinder.addEventListener("click", () => {
			adjustColorPicker(colorPickerDetailFinder, "BLACK");
		});
	}

	//メニューボタン押下処理 
	if (qrMenuBtn != undefined && qrMenuBtn != null) {
		for (i = 0; i < qrMenuBtn.length; i++) {
			qrMenuBtn[i].addEventListener('click', (event) => {
				if (!event.currentTarget.classList.contains('selected')) {
					changeQrType(event.currentTarget);
				}
			});
		}
	}

	//メニュー押下処理 
	if (qrMenu != undefined && qrMenu != null) {
		for (i = 0; i < qrMenu.length; i++) {
			qrMenu[i].addEventListener('click', (event) => {
				if (!event.currentTarget.classList.contains('selected_nav')) {
					changeQrType(event.currentTarget);
				}
			});
		}
	}

	if (inputElmDatas != undefined && inputElmDatas != null) {
		for (i = 0; i < inputElmDatas.length; i++) {
			inputElmDatas[i].addEventListener('input', (event) => {
				inputValueChanged(event.currentTarget);
			});
		}
	}

	//拡張ボタン押下処理 
	if (styleToggleBtn != undefined && styleToggleBtn != null) {
		styleToggleBtn.addEventListener('click', (e) => {
			controlStyleToggleBtn(e.currentTarget);
		});
	}

	if (emptyLayer != undefined && emptyLayer != null) {
		emptyLayer.addEventListener('click', (e) => {
			closeDiv(navBtn);
			closeDiv(navLang);
			closeDiv(navUser);
			closePopup('popup_login');
			closePopup('popup_description_static_dynamic');
			closePopup('popup_bulk_qr_codes');
		});
	}

	window.addEventListener('resize', function () {
		resizeDisp();
	});

	if (fileSelect != undefined && fileSelect != null) {
		fileSelect.addEventListener("click", (e) => {
			if (fileElem) {
				fileElem.click();
			}
		}, false);
	}

	if (uploadFileSelect != undefined && uploadFileSelect != null) {
		uploadFileSelect.addEventListener("click", (e) => {
			if (uploadFileElem) {
				uploadFileElem.click();
			}
		}, false);
	}

	if (switchStaticDynamic != undefined && switchStaticDynamic != null) {
		switchStaticDynamic.addEventListener("click", (e) => {
			arrangeStaticDynamicSwitcher();
		});
	}

	if (switchLogo != undefined && switchLogo != null) {
		switchLogo.addEventListener("click", (e) => {
			arrangeLogoNeededSwitcher(!e.currentTarget.checked);
		});
	}

	if (switchLogoSize != undefined && switchLogoSize != null) {
		switchLogoSize.addEventListener("click", (e) => {
			arrangeLogoSizeSwitcher(true);
		});
	}

	if (switchImgType != undefined && switchImgType != null) {
		switchImgType.addEventListener("click", (e) => {
			arrangeImgTypeSwitcher();
		});
	}

	if (customizeNavMenus != undefined && customizeNavMenus != null) {
		for (i = 0; i < customizeNavMenus.length; i++) {
			customizeNavMenus[i].addEventListener('click', (e) => {
				arrangeCustomizeNavInput(e.currentTarget);
			});
		}
	}

	if (profileToggleBtn != undefined && profileToggleBtn != null) {
		profileToggleBtn.addEventListener('click', (e) => {
			controlProfileToggleBtn(e.currentTarget);
		});
	}

	if (uploadFileSelect != undefined && uploadFileSelect != null) {
		uploadFileSelect.addEventListener("click", (e) => {
			if (uploadFileElem) {
				uploadFileElem.click();
			}
		}, false);
	}

	if (userUpdateBtn != undefined && userUpdateBtn != null) {
		userUpdateBtn.addEventListener('click', () => {
			userUpdate();
		});
	}
}